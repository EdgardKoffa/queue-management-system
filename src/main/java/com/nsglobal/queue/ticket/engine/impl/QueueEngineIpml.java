package com.nsglobal.queue.ticket.engine.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.enums.CounterActions;
import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.common.enums.TicketHistoryActions;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.entity.CounterHistory;
import com.nsglobal.queue.counter.repository.CounterHistoryRepository;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.notification.service.NotificationService;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.engine.QueueEngine;
import com.nsglobal.queue.ticket.engine.factory.QueueStrategyFactory;
import com.nsglobal.queue.ticket.engine.strategy.QueueSelectionStrategy;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.entity.TicketHistory;
import com.nsglobal.queue.ticket.mapper.TicketMapper;
import com.nsglobal.queue.ticket.repository.TicketHistoryRepository;
import com.nsglobal.queue.ticket.repository.TicketRepository;
import com.nsglobal.queue.user.entity.User;
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
	private final CounterHistoryRepository counterHistoryRepo;

	private final NotificationService notification;
	
	// fabrique de moteur de strategie
	public final QueueStrategyFactory factory;
	// wesocket messaging
	private final QueueNotificationService notificationService;

	private void reccordTicketHistory(Ticket tkt, TicketHistoryActions action, String comment) {
		// Sauvegarde Historique
		TicketHistory histo = TicketHistory.builder().ticket(tkt).action(action).comment(comment)
				.reccordTime(LocalDateTime.now()).build();
		ticketHistoRepo.save(histo);
	}

	private void reccordCounterHistory(Counter counter, CounterActions action, String ipAddress, String operator,
			String comment) {

		CounterHistory histo = CounterHistory.builder().action(action).assigned_operator(operator).ipAdress(ipAddress)
				.reccordTime(LocalDateTime.now()).counter(counter).comment(comment).build();

		counterHistoryRepo.save(histo);
	}

	private Counter getCounterById(Long id) {
		Counter counter = counterRepository.findById(id).orElseThrow(
				() -> new RuntimeException("Ce guichet n'est pas retrouvé dans le systeme pour l'appel du ticket"));
		if (counter.getActive() == false) {
			throw new RuntimeException("Ce guichet est inactif, impossible d'appeler un ticket");
		}
		if (counter.getOperator() == null) {
			throw new RuntimeException(
					"Aucun opérateur(agent de guichet) n'est assigné à ce guichet. Contactez l'administrateur svp!");
		}

		return counter;
	}

	/**
	 * Debut du cycle de vie d'un ticket : appel du ticket
	 */
	@Override
	public TicketResponseDto callNextTicket(Long counterId) {

		Counter counter = getCounterById(counterId);

		if (counter.getStatus() != CounterStatus.OPEN) {
			throw new RuntimeException(
					"Ce guichet n'est pas ouvert pour appeler un ticket. Contactez l'administrateur du systeme svp! ");
		}

		User operator = counter.getOperator();

		Branch branch = counter.getBranch();

		// recuperation du methode de la strategie selon l'agorithm de file configure
		// pour l'agence
		QueueSelectionStrategy selectionStrategy = factory.getStrategy(branch.getQueueAlgorithm());

		// appel du prochain ticke en mode strategie de l'agence selon la configuration
		Ticket ticket = selectionStrategy.selectNextTicket(counter);

		if (ticket == null) {
			throw new RuntimeException("Pas de ticket dans la file d'attente");
		}
		counter.setStatus(CounterStatus.BUSY);
		counterRepository.save(counter);

		reccordCounterHistory(counter, CounterActions.BUSY, null, operator.getUserName(),
				"Ticket appelé, ce guichet est est occupé.");
		/*
		 * //appel du prochain ticke en mode FIFO
		 * ticketrepository.findFirstByBranchAndStatusOrderByPriorityAscIssueTimeAsc(
		 * branch, TicketStatus.WAITING ) .orElseThrow();
		 */

		// Mise a jour du ticket WAITING => IN_PROGRESS
		LocalDateTime call_start_time = LocalDateTime.now();

		Long waittingTime = (long) (call_start_time.getMinute() - ticket.getIssueTime().getMinute());

		ticket.setCounter(counter);
		ticket.setStatus(TicketStatus.IN_PROGRESS);
		ticket.setCallTime(call_start_time);
		ticket.setStartTime(call_start_time);
		ticket.setEstimatedWaiting(waittingTime);
		Ticket calledticket = ticketrepository.save(ticket);

		// Sauvegarde Historique
		reccordTicketHistory(calledticket, TicketHistoryActions.STARTED, "Appel du ticket en FIFO ");
		//sms sending
		notification.sendTicketCalled(calledticket);
		
		notificationService.notifyTicketCalled(ticket);
		notificationService.publishDashboard();
		notificationService.publishCounter(counter);
		notificationService.publishTicket(calledticket);
		// =======display screens=======================
		notificationService.publishAnnouncement(calledticket.getId());
		notificationService.publishCounterDisplay(counterId);
		notificationService.publishWaitingScreen();

		return ticketMapper.toTicketResponsDto(calledticket);
	}

	/**
	 * terminer de servir un ticket
	 */
	@Override
	public TicketResponseDto finishTicket(Long counterId) {

		Counter counter = getCounterById(counterId);

		if (counter.getStatus() != CounterStatus.BUSY) {
			throw new RuntimeException("Ce guichet n'est pas occupé, donc aucun ticket encours");
		}

		User operator = counter.getOperator();

		// recuperer le ticket encours a cet guichet pour le completer
		Ticket ticketInProgress = ticketrepository.findByCounterIdAndStatus(counterId, TicketStatus.IN_PROGRESS)
				.orElseThrow(() -> new RuntimeException("Pas de ticket encours pour ce guichet."));

		// calcul du temps estime
		LocalDateTime end_time = LocalDateTime.now();
		LocalDateTime startTime = ticketInProgress.getStartTime();

		Long estimateTime = (long) (end_time.getMinute() - startTime.getMinute());

		// mise a jour du ticke teriner
		ticketInProgress.setEndTime(LocalDateTime.now());
		ticketInProgress.setStatus(TicketStatus.COMPLETED);
		ticketInProgress.setEstimatedOperation(estimateTime);

		Ticket finishedTicket = ticketrepository.save(ticketInProgress);

		// reouverture du guichet pour appeler le ticket suivant
		counter.setStatus(CounterStatus.OPEN);
		counterRepository.save(counter);

		// historiques
		reccordCounterHistory(counter, CounterActions.OPENED, null, operator.getUserName(),
				"Le guichet est à nouveau disponible après avoir completer le ticket précédent.");

		reccordTicketHistory(finishedTicket, TicketHistoryActions.COMPLETED,

				"Le ticket est terminé");

		// notifications
		notification.sendTicketCompleted(finishedTicket);
		
		notificationService.publishCounter(counter);
		notificationService.notifyTicketCalled(finishedTicket);
		notificationService.publishDashboard();
		notificationService.publishTicket(finishedTicket);
		// =======display screens=======================
		// notificationService.publishAnnouncement(finishedTicket.getId());
		notificationService.publishCounterDisplay(counterId);
		notificationService.publishWaitingScreen();

		return ticketMapper.toTicketResponsDto(finishedTicket);
	}

	/**
	 * transferer un ticket appeler a un nouveau guichet
	 * 
	 * @Long curentCounterId: guichet qui a appeler le ticket
	 * @Long destinationCounterId: nouveau guichet vers lequel le ticket est
	 *       transferer
	 */
	@Override
	public TicketResponseDto transferTicket(Long curentCounterId, Long destinationCounterId) {

		Counter counter = getCounterById(curentCounterId);

		if (counter.getStatus() != CounterStatus.BUSY) {
			throw new RuntimeException("Ce guichet n'est pas occupé, donc aucun ticket encours pour le transfer");
		}

		User currentoperator = counter.getOperator();

		// recuperer le ticket encours a cet guichet pour le completer
		Ticket ticketInProgress = ticketrepository.findByCounterIdAndStatus(curentCounterId, TicketStatus.IN_PROGRESS)
				.orElseThrow(() -> new RuntimeException("Pas de ticket encours pour ce guichet."));

		// guichet de destination du transfert du ticket
		Counter destinationCounter = counterRepository
				.findByIdAndBranchIdAndStatus(destinationCounterId, counter.getBranch().getId(), CounterStatus.OPEN)
				.orElseThrow(() -> new RuntimeException("Aucun guichet disponible pour le transfert du ticket"));

		if (destinationCounter.getOperator() == null) {
			throw new RuntimeException("Le guichet de transfert 'a aucun opérateur assigné");
		}
		// rendre le guichet courant disponible
		counter.setStatus(CounterStatus.OPEN);
		counterRepository.save(counter);

		// Le ticket est transfert au guichet avec tjrs son etat de progression
		destinationCounter.setStatus(CounterStatus.BUSY);
		counterRepository.save(destinationCounter);
		ticketInProgress.setCounter(destinationCounter);

		// ------------ou---------------------------------
		// remettre le ticket dans la queue
		// ticketInProgress.setStatus(TicketStatus.WAITING);
		// ticketInProgress.setCounter(null);

		Ticket transfered = ticketrepository.save(ticketInProgress);

		// historiques:
		reccordCounterHistory(counter, CounterActions.OPENED, null, currentoperator.getUserName(),
				"Le guichet est à nouveau disponible après le transfert du ticket précédent.");
		reccordCounterHistory(destinationCounter, CounterActions.BUSY, null,
				destinationCounter.getOperator().getUserName(),
				"Le guichet n'est plus disponible après le transfert du ticket depuis un autre guichet par %s."
						.formatted(currentoperator.getUserName()));

		reccordTicketHistory(transfered, TicketHistoryActions.TRANSFERRED, "Le ticket est transferé");

		// notifications
		notification.sendTicketTransferred(ticketInProgress);
		notificationService.notifyTicketCalled(transfered);
		notificationService.publishDashboard();
		notificationService.publishTicket(transfered);
		// =======display screens=======================
		notificationService.publishCounterDisplay(curentCounterId);
		notificationService.publishCounterDisplay(destinationCounterId);
		notificationService.publishWaitingScreen();

		return ticketMapper.toTicketResponsDto(transfered);
	}

	@Override
	public TicketResponseDto cancelTicket(Long counterId) {
		Counter counter = getCounterById(counterId);

		if (counter.getStatus() != CounterStatus.BUSY) {
			throw new RuntimeException("Ce guichet n'est pas occupé, donc aucun ticket encours");
		}

		User operator = counter.getOperator();

		// recuperer le ticket encours a cet guichet pour le completer
		Ticket ticketInProgress = ticketrepository.findByCounterIdAndStatus(counterId, TicketStatus.IN_PROGRESS)
				.orElseThrow(() -> new RuntimeException("Pas de ticket encours pour ce guichet."));

		ticketInProgress.setStatus(TicketStatus.CANCELLED);
		ticketInProgress.setCounter(null);
		Ticket canceledTicket = ticketrepository.save(ticketInProgress);

		counter.setStatus(CounterStatus.OPEN);
		counterRepository.save(counter);

		// historique
		reccordCounterHistory(counter, CounterActions.BUSY, null, operator.getUserName(),
				"Le guichet est à nouveau disponible après l'annulation du ticket précédent.");

		reccordTicketHistory(canceledTicket, TicketHistoryActions.CANCELLED, "Le ticket est annulé");

		// notificationt
		notification.sendTicketCancelled(canceledTicket);
		notificationService.notifyTicketCalled(canceledTicket);
		notificationService.publishDashboard();
		notificationService.publishTicket(canceledTicket);
		// =======display screens=======================
		notificationService.publishCounterDisplay(counterId);
		notificationService.publishWaitingScreen();
		return ticketMapper.toTicketResponsDto(canceledTicket);
	}

}
