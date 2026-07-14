package com.nsglobal.queue.notification.service;

import com.nsglobal.queue.notification.dto.NotificationResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;

public interface NotificationService {
	
	  /**
     * Envoie un SMS générique.
     */
    NotificationResponseDto sendSms(String phoneNumber, String message);

    /**
     * Envoie un email générique.
     */
    NotificationResponseDto sendEmail(String email,
                                      String subject,
                                      String message);

    /**
     * Notification après création d'un ticket.
     */
    void sendTicketCreated(Ticket ticket);

    /**
     * Notification lorsqu'un ticket est appelé.
     */
    void sendTicketCalled(Ticket ticket);

    /**
     * Notification lorsqu'un ticket est transféré.
     */
    void sendTicketTransferred(Ticket ticket);

    /**
     * Notification lorsqu'un ticket est terminé.
     */
    void sendTicketCompleted(Ticket ticket);

    /**
     * Notification lorsqu'un ticket est annulé.
     */
    void sendTicketCancelled(Ticket ticket);

}
