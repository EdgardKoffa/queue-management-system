package com.nsglobal.queue.ticket.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.bankservice.repository.BankServiceRepository;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.enums.TicketHistoryActions;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.repository.CounterRepository;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
	
	private final TicketSequenceRepository ticketSequenceRepository;
	private final TicketRepository ticketrepository;
	private final BranchRepository branchRepository;
	private final BankServiceRepository bksRepository;
	private final CounterRepository counterRepository;
	private final TicketMapper ticketMapper;
	
	private final TicketHistoryRepository ticketHistoRepo;

	@Override
	@Transactional
	public TicketResponseDto create(TicketRequestDto dto) {
		//Si aucune séquence n'existe aujourd'hui on commence par 1
		
		//chargement des relations
		Branch branch=branchRepository.findById(dto.getBranchId()).orElseThrow();
		BankService service=bksRepository.findById(dto.getServiceId()).orElseThrow();
		
		//recherche de dernier ticket pour avoir le numero suivant
		TicketSequence sequence = ticketSequenceRepository
				.findForUpdate(branch, service, LocalDate.now())
				.orElseGet(() -> {
			TicketSequence ts = new TicketSequence();
			ts.setBranch(branch); ts.setService(service); 
			ts.setSequenceDate(LocalDate.now()); 
			ts.setLastNumber(0);
			return ticketSequenceRepository.save(ts); 
			}
		);
		//Incrémentation et sauvegarde de la sequence des tickets
		sequence.setLastNumber(sequence.getLastNumber() + 1); 
		ticketSequenceRepository.save(sequence);
		
		/* Génération du numéro:
		 * Supposons : Préfixe = D (pour depot) LastNumber = 8 => D008
		 */
		String ticketNumber =service.getPrefix().concat(
				String.format("%03d", sequence.getLastNumber())//formate les numero :008
				);
		//Création du ticket
		Ticket ticket = new Ticket();
		ticket.setBranch(branch); 
		ticket.setService(service); 
		ticket.setSequenceNumber(sequence.getLastNumber());
		ticket.setTicketNumber(ticketNumber); 
		ticket.setStatus(TicketStatus.WAITING); 
		ticket.setPriority(dto.getPriority()); 
		ticket.setIssueTime(LocalDateTime.now()); 
		ticket.setTicketDate(LocalDate.now());
		
		Ticket ticketSaved =ticketrepository.save(ticket);
		
		
		return ticketMapper.toTicketResponsDto(ticketSaved);
	}

	@Override
	public TicketResponseDto callNextTicket(Long counterId) {
		//chargement du guichet appelant
		Counter counter=counterRepository.findById(counterId).orElseThrow();
		Branch branch=counter.getBranch();
		
		Ticket ticket=ticketrepository.findFirstByBranchAndStatusOrderByPriorityAscIssueTimeAsc(
				branch, 
				TicketStatus.WAITING
				)
				.orElseThrow();
		ticket.setCounter(counter);
		ticket.setStatus(TicketStatus.CALLED);
		ticket.setCallTime(LocalDateTime.now());
		Ticket calledticket=ticketrepository.save(ticket);
		
		TicketHistory histo=new TicketHistory();
		histo.setTicket(calledticket);
		histo.setAction(TicketHistoryActions.CALLED);
		ticketHistoRepo.save(histo);
		
		return ticketMapper.toTicketResponsDto(calledticket);
	}

}
