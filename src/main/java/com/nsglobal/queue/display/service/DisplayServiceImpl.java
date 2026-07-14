package com.nsglobal.queue.display.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.display.dto.AnnouncementDto;
import com.nsglobal.queue.display.dto.CounterDisplayDto;
import com.nsglobal.queue.display.dto.DisplayTicketDto;
import com.nsglobal.queue.display.dto.WaittingScreenDto;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisplayServiceImpl implements DisplayService {
	
	private final TicketRepository ticketReposit;
	
	private final CounterRepository counterRepository;
	
	private DisplayTicketDto ticketToDisplayticketDto(Ticket tkt) {
		
		DisplayTicketDto dto=DisplayTicketDto
				.builder()
				.service(tkt.getService().getName())
				.ticketNumber(tkt.getTicketNumber())
				.callTime(tkt.getCallTime())//or startTime
				.counter(tkt.getCounter().getName())
				.tickedId(tkt.getId())
				.operator(tkt.getCounter().getOperator().getUserName())
				.build();
		return dto;
	}
	
	@Override
	public WaittingScreenDto getWaitingScreen() {
		
		/*recuperer la liste des tickets en attente
		 * au jour actuel ordnner du premier creer
		*/
		List<Ticket> tickets=ticketReposit
				.findByStatusAndTicketDateOrderByStartTimeAsc(
						TicketStatus.WAITING, 
						LocalDate.now()
						);
		
		//transfomation de la list ticket en list display ticketdto
		List<DisplayTicketDto> results=tickets.stream()
				.map((Ticket tkt)->{
					DisplayTicketDto dto=ticketToDisplayticketDto(tkt);
					return dto;
				}).toList();
		
		return WaittingScreenDto.builder()
				.calledTickets(results)
				.build();
	}

	@Override
	public CounterDisplayDto getCounterDisplay(Long counterId) {
		
		Counter counter=counterRepository.findById(counterId)
				.orElseThrow(()->new RuntimeException("Le guichet est introuvable"));
		if(counter.getActive()==false) {
			throw new RuntimeException("Le guichet est inactif. Contactez l'administrateur du système.");
		}
		
		Ticket ticket=ticketReposit
				.findByStatusAndTicketDateAndCounter(
						counterId, 
						LocalDate.now(), 
						TicketStatus.IN_PROGRESS
						);
		if(ticket==null) {
			throw new RuntimeException("Le ticket est introuvable. Contactez l'administrateur du système.");
		}
		
		CounterDisplayDto cdto=CounterDisplayDto
				.builder()
				.counterId(counterId)
				.counterName(counter.getName())
				.ticketDto(ticketToDisplayticketDto(ticket))
				.build();
		
		return cdto;
	}

	@Override
	public AnnouncementDto getAnnouncement(Long ticketId) {
		
		Ticket ticket=ticketReposit.findByIdAndStatus(ticketId, TicketStatus.IN_PROGRESS);
		
		if(ticket==null) {
			throw new RuntimeException("Le ticket est introuvable. Contactez l'administrateur du système.");
		}
		
		String counterName=ticket.getCounter().getName();
		String ticketNumber=ticket.getTicketNumber();
		
		String messageVocal="Ticket %s, veuillez vous présenter au guichet %s, s'il vous plais.".formatted(ticketNumber,counterName);// message a lire sut l'ecran tv
		
		AnnouncementDto dto=AnnouncementDto
				.builder()
				.Counter(counterName)
				.message(messageVocal)
				.tikcetNumber(ticketNumber)
				.build();
		
		return dto;
	}

}
