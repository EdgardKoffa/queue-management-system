package com.nsglobal.queue.ticket.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.bankservice.repository.BankServiceRepository;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.enums.TicketHistoryActions;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.notification.service.NotificationService;
import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.entity.TicketHistory;
import com.nsglobal.queue.ticket.entity.TicketSequence;
import com.nsglobal.queue.ticket.mapper.TicketMapper;
import com.nsglobal.queue.ticket.repository.TicketHistoryRepository;
import com.nsglobal.queue.ticket.repository.TicketRepository;
import com.nsglobal.queue.ticket.repository.TicketSequenceRepository;
import com.nsglobal.queue.ticket.service.TicketService;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

	private final TicketSequenceRepository ticketSequenceRepository;
	private final TicketRepository ticketrepository;
	private final TicketHistoryRepository hisotory;
	private final BranchRepository branchRepository;
	private final BankServiceRepository bksRepository;
	private final TicketMapper ticketMapper;
	
	private final AuditService audiSrvc;
	
	private final QueueNotificationService ws_notification;
	private final NotificationService notification;

	@Override
	@Transactional
	public TicketResponseDto create(TicketRequestDto dto) {
		// Si aucune séquence n'existe aujourd'hui on commence par 1

		// chargement des relations
		Branch branch = branchRepository.findById(dto.getBranchId())
				.orElseThrow(
						()->{
							String msg="L'agence n'est pas disponible pour ce ticket";
							audiSrvc.log(
									AuditActionEnum.CREATE_TICKET, 
									ModulesNameEnum.TICKET, 
									"❌ Erreur de création du ticket. cause: %s ".formatted(msg), 
									false);
						return	new RuntimeException(msg);
						}
						);
		
		BankService service = bksRepository.findById(dto.getServiceId())
				.orElseThrow(
						()->{
							String msg="Le service n'est pas disponible pour ce ticket";
							audiSrvc.log(
									AuditActionEnum.CREATE_TICKET, 
									ModulesNameEnum.TICKET, 
									"❌ Erreur de création du ticket. cause: %s ".formatted(msg), 
									false);
							return new RuntimeException(msg);
						}
						);

		// recherche de dernier ticket pour avoir le numero suivant
		TicketSequence sequence = ticketSequenceRepository.findForUpdate(branch, service, LocalDate.now())
				.orElseGet(() -> {
					TicketSequence ts = new TicketSequence();
					ts.setBranch(branch);
					ts.setService(service);
					ts.setSequenceDate(LocalDate.now());
					ts.setLastNumber(0);
					return ticketSequenceRepository.save(ts);
				});
		// Incrémentation et sauvegarde de la sequence des tickets
		sequence.setLastNumber(sequence.getLastNumber() + 1);
		ticketSequenceRepository.save(sequence);

		/*
		 * Génération du numéro: Supposons : Préfixe = D (pour depot) LastNumber = 8 =>
		 * D008
		 */
		String ticketNumber = service.getPrefix().concat(String.format("%03d", sequence.getLastNumber())// formate les
																										// numero :008
		);
		// Création du ticket
		Ticket ticket = new Ticket();
		ticket.setBranch(branch);
		ticket.setService(service);
		ticket.setSequenceNumber(sequence.getLastNumber());
		ticket.setTicketNumber(ticketNumber);
		ticket.setStatus(TicketStatus.WAITING);
		ticket.setPriority(dto.getPriority());
		ticket.setIssueTime(LocalDateTime.now());
		ticket.setTicketDate(LocalDate.now());
		//ticket.setCustomerName(dto.)
		if(dto.getPhone()!=null&&dto.getPhone().isBlank()==false&&dto.getPhone().isEmpty()==false) {
			ticket.setPhone(dto.getPhone());
		}
		if(dto.getEmail()!=null&&dto.getEmail().isBlank()==false&&dto.getEmail().isEmpty()==false) {
			ticket.setEmail(dto.getEmail());
		}

		Ticket ticketSaved = ticketrepository.save(ticket);
		
		//ws_notification
		//envois message sms si l'option est choisi sur le kiosque
	     notification.sendTicketCreated(ticketSaved);
	     
		ws_notification.notifyTicketCalled(ticketSaved);
		ws_notification.publishTicket(ticketSaved);
		ws_notification.publishDashboard();
		
		//historique
		TicketHistory rec=TicketHistory
				.builder()
				.action(TicketHistoryActions.CREATED)
				.ticket(ticketSaved)
				.comment("Ticket créé.")
				.build();
		hisotory.save(rec);
		audiSrvc.log(
				AuditActionEnum.CREATE_TICKET, 
				ModulesNameEnum.TICKET, 
				"✅ Création du ticket N "+ticketNumber, 
				true);
		return ticketMapper.toTicketResponsDto(ticketSaved);
	}

	
	@Override
	public Ticket findById(Long id) {

		return ticketrepository
						.findById(id)
						.orElseThrow(
								()-> new RuntimeException("Le ticket est introuvable.")
								);
	}

	@Override
	public List<TicketResponseDto> findAll() {

		return ticketMapper.toListTicketResponsDto(
				ticketrepository.findAll()
				);
	}

	@Override
	public void remove(Long id) {
		ticketrepository.deleteById(id);
	}


	@Override
	public void proccessingAbsentTicket(Long timeout) {
		
		
		List<Ticket> inprogressTickets=ticketrepository.
				findByStatus(TicketStatus.IN_PROGRESS);
		log.info("total called ticket: {}",inprogressTickets.size());
		for (Ticket ticket : inprogressTickets) {
			//conversion de @timeout min a partir de l'instant en loca date time
			Duration duration=Duration.between(ticket.getCallTime(), LocalDateTime.now()) ;
			
			if(duration.toMinutes()<timeout) {
				continue;
			}
			
			ticket.setStatus(TicketStatus.ABSENT);
			Ticket ticketSaved=ticketrepository.save(ticket);
			
			TicketHistory rec=TicketHistory
					.builder()
					.action(TicketHistoryActions.ABSENT)
					.ticket(ticketSaved)
					.comment("Ticket expiré automatiquement.")
					.build();
			hisotory.save(rec);
			ws_notification.publishTicket(ticketSaved);
			ws_notification.publishDashboard();
			
		}
		
		
		
	}

	

}
