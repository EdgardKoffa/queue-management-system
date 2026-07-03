package com.nsglobal.queue.ticket.engine.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.enums.TicketHistoryActions;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.engine.QueueEngine;
import com.nsglobal.queue.ticket.engine.factory.QueueStrategyFactory;
import com.nsglobal.queue.ticket.engine.strategy.QueueSelectionStrategy;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.entity.TicketHistory;
import com.nsglobal.queue.ticket.mapper.TicketMapper;
import com.nsglobal.queue.ticket.repository.TicketHistoryRepository;
import com.nsglobal.queue.ticket.repository.TicketRepository;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QueueEngineIpml implements QueueEngine {

	private final TicketRepository ticketrepository;
	private final CounterRepository counterRepository;
	private final TicketHistoryRepository ticketHistoRepo;
	private final TicketMapper ticketMapper;

	// fabrique de moteur de strategie
	public final QueueStrategyFactory factory;

	// wesocket messaging
	private final QueueNotificationService notificationService;

	@Override
	public TicketResponseDto callNextTicket(Long counterId) {
		Counter counter = counterRepository.findById(counterId).orElseThrow();
		Branch branch = counter.getBranch();

		// recuperation du methode de la strategie selon l'agorithm de file configure
		// pour l'agence
		QueueSelectionStrategy selectionStrategy = factory.getStrategy(branch.getQueueAlgorithm());

		// appel du prochain ticke en mode strategie de l'agence selon la configuration
		Ticket ticket = selectionStrategy.selectNextTicket(counter);
		/*
		 * //appel du prochain ticke en mode FIFO
		 * ticketrepository.findFirstByBranchAndStatusOrderByPriorityAscIssueTimeAsc(
		 * branch, TicketStatus.WAITING ) .orElseThrow();
		 */

		// Mise a jour du ticket WAITING => CALLED
		ticket.setCounter(counter);
		ticket.setStatus(TicketStatus.CALLED);
		ticket.setCallTime(LocalDateTime.now());
		Ticket calledticket = ticketrepository.save(ticket);

		// Sauvegarde Historique
		TicketHistory histo = TicketHistory.builder().ticket(calledticket).action(TicketHistoryActions.CALLED)
				.comment("Appel du ticket en FIFO ").build();
		ticketHistoRepo.save(histo);

		notificationService.notifyTicketCalled(ticket);

		return ticketMapper.toTicketResponsDto(calledticket);
	}

}
