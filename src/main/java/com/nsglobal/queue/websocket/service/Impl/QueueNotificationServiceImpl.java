package com.nsglobal.queue.websocket.service.Impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.websocket.dto.QueueDisplayDto;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueNotificationServiceImpl implements QueueNotificationService {

	// permet d'envoyer un message.
	private final SimpMessagingTemplate messagingTemplate;

	@Override
	public void notifyTicketCalled(Ticket ticket) {

		// construire les elements du message a difuser
		QueueDisplayDto dto = QueueDisplayDto.builder()

				.ticketId(ticket.getId())

				.ticketNumber(ticket.getTicketNumber())

				.service(ticket.getService().getName())

				.counter(ticket.getCounter().getName())

				.branch(ticket.getBranch().getName())

				.callTime(ticket.getCallTime())

				.build();

		// envoie de message
		messagingTemplate.convertAndSend("/topic/branch/" + ticket.getBranch().getId(), dto);

	}

}
