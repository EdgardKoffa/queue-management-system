package com.nsglobal.queue.websocket.service;

import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.notification.dto.NotificationEventDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;

public interface QueueNotificationService {

	public void notifyTicketCalled(Ticket ticket);

	public void publishKiosk(TicketResponseDto ticket);

	public void publishCounter(Counter c);

	public void publishTicket(Ticket ticket);

	public void publishDashboard();

//===============ecrans=====================
	public void publishWaitingScreen();

	public void publishCounterDisplay(Long counterId);

	public void publishAnnouncement(Long ticketId);
//===============sms email ==========================
	public void sendindSmsWsNotification(NotificationEventDto dto);

}
