package com.nsglobal.queue.websocket.service;

import com.nsglobal.queue.ticket.entity.Ticket;

public interface QueueNotificationService {

	public void notifyTicketCalled(Ticket ticket);

}
