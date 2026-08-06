package com.nsglobal.queue.websocket.service.Impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.nsglobal.queue.counter.dto.CounterResponseDto;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.dashboard.dto.DashboardDto;
import com.nsglobal.queue.dashboard.dto.DashboardResponseDto;
import com.nsglobal.queue.dashboard.service.DashboardService;
import com.nsglobal.queue.display.service.DisplayService;
import com.nsglobal.queue.notification.dto.NotificationEventDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.mapper.TicketMapper;
import com.nsglobal.queue.ticket.repository.TicketRepository;
import com.nsglobal.queue.websocket.dto.QueueDisplayDto;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueNotificationServiceImpl implements QueueNotificationService {

	// permet d'envoyer un message.
	private final SimpMessagingTemplate messagingTemplate;
	
	private final DisplayService displayService;
	
	private final DashboardService dashboardService;
	//private final TicketMapper ticketMapper;
	
	
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

	@Override
	public void publishCounter(Counter c) {
		
		CounterResponseDto dto=CounterResponseDto
				.builder()
				.id(c.getId())
				.active(c.getActive())
				.branchName(c.getBranch().getName())
				.name(c.getName())
				.number(c.getNumber())
				.operatorUserName(c.getOperator().getUserName())
				.status(c.getStatus())
				.build();
		// envoie de message
				messagingTemplate.convertAndSend("/topic/counter/" + c.getId(), dto);

	}

	@Override
	public void publishTicket(Ticket ticket) {
		
		TicketResponseDto dto=TicketResponseDto
				.builder()
				.id(ticket.getId())
				.branchId(ticket.getBranch().getId())
				.branchName(ticket.getBranch().getName())
				.counterName(ticket.getCounter().getName())
				.priority(ticket.getPriority())
				.issueTime(ticket.getIssueTime())
				.serviceName(ticket.getService().getName())
				.ticketNumber(ticket.getTicketNumber())
				.status(ticket.getStatus())
				.ticketDate(ticket.getTicketDate())
				.endTime(ticket.getEndTime())
				.startTime(ticket.getStartTime())
				.build();
		// envoie de message
		messagingTemplate.convertAndSend("/topic/tickets/" + ticket.getId(), dto);

	}

	@Override
	public void publishDashboard() {
		
		 DashboardResponseDto dashboard =
	                dashboardService.getDashboard();

		messagingTemplate.convertAndSend(
                "/topic/dashboard",
                dashboard
        );

	}
	
	public void publishKiosk(TicketResponseDto ticket) {

	    messagingTemplate.convertAndSend(
	            "/topic/kiosk",
	            ticket
	    );

	}

	@Override
	public void publishWaitingScreen() {

	messagingTemplate.convertAndSend("/topic/display/waitting/" , displayService.getWaitingScreen());

	}

	@Override
	public void publishCounterDisplay(Long counterId) {

		
	 messagingTemplate.convertAndSend("/topic/display/counter" + counterId, displayService.getCounterDisplay(counterId));

	}

	@Override
	public void publishAnnouncement(Long ticketId) {

	messagingTemplate.convertAndSend("/topic/display/announcement/" +ticketId, displayService.getAnnouncement(ticketId));

		
	}
	
	public void sendindSmsWsNotification(NotificationEventDto dto) {
		messagingTemplate.convertAndSend("/topic/display/notifications/",dto);
	}
	

}
