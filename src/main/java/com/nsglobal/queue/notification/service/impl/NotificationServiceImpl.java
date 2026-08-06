package com.nsglobal.queue.notification.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.common.config.AppConfig;
import com.nsglobal.queue.notification.dto.NotificationEventDto;
import com.nsglobal.queue.notification.dto.NotificationResponseDto;
import com.nsglobal.queue.notification.email.EmailProvider;
import com.nsglobal.queue.notification.service.NotificationService;
import com.nsglobal.queue.notification.sms.SmsProvider;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
	
	private final AppConfig appconf;
	
	private final SmsProvider smsProvider;
	
	private final EmailProvider emailProvider;
	
	private final QueueNotificationService wspusher;
	
	private final AuditService audit;
	 
	private void sendMessage(String msg,Ticket ticket) {
		
		if(ticket.getPhone()==null||
				ticket.getPhone().isBlank()||
				ticket.getPhone().isEmpty()) {
			audit.log(
		    		AuditActionEnum.SEND_SMS, 
		    		ModulesNameEnum.NOTIFICATION, 
		    		"❌ Echec d'envoie du message, le numéro de téléphone est invalide", 
		    		false);
			return;
		}
		 
		 smsProvider.send(ticket.getPhone(), msg);
		 audit.log(
				 AuditActionEnum.SEND_SMS, 
		    		ModulesNameEnum.NOTIFICATION, 
		    		"✅ Succès d'envoie du message au numéro de téléphone %s".formatted(ticket.getPhone()), 
		    		true);
	}
	@Override
	public NotificationResponseDto sendSms(String phoneNumber, String message) {
		
		smsProvider.send(phoneNumber, message);
		String msg="Messsage envoyé avec succès.";
		audit.log(
				 AuditActionEnum.SEND_SMS, 
		    		ModulesNameEnum.NOTIFICATION, 
		    		"✅ Succès d'envoie du message au numéro de téléphone %s".formatted(phoneNumber), 
		    		true);
		wspusher.sendindSmsWsNotification(
				NotificationEventDto
				.builder()
				.message(msg)
				.channel("SMS")
				.dateTime(LocalDateTime.now())
				.recipient(phoneNumber)
				.type("SMS")
				.success(true)
				.build()
				);
		
		return NotificationResponseDto.builder()
				.channel("SMS")
				.message("".formatted(msg))
				.recipient(phoneNumber)
				.success(true)
				.build();
	}

	@Override
	public NotificationResponseDto sendEmail(String email, String subject, String message) {
		
		emailProvider.send(email, subject, message);
		
		String msg="Messsage envoyé avec succès.";
		audit.log(
				 AuditActionEnum.SEND_SMS, 
		    		ModulesNameEnum.NOTIFICATION, 
		    		"✅ Succès d'envoie du message. Email: %s".formatted(email), 
		    		true);
		wspusher.sendindSmsWsNotification(
				NotificationEventDto
				.builder()
				.message(msg)
				.channel("EMAIL")
				.dateTime(LocalDateTime.now())
				.recipient(email)
				.type("EMAIL")
				.success(true)
				.build()
				);
		
		return NotificationResponseDto.builder()
				.channel("Email")
				.message("".formatted(msg))
				.recipient(email)
				.success(true)
				.build();
	}

	@Override
		public void sendTicketCreated(Ticket ticket) {
		String msg= """
	            Bienvenue chez %s.

	            Votre ticket %s a été créé.

	            Service : %s

	            Merci de patienter.
	            """;
		String message = String.format(
		           msg,
		            appconf.getEnterpriseName(),
		            ticket.getTicketNumber(),
		            ticket.getService().getName()
		    );
		sendMessage(message,ticket);
		
	}

	@Override
	public void sendTicketCalled(Ticket ticket) {
		String msg= """
	        Ticket %s
            Veuillez vous présenter
            au guichet %s.
	            """;
		String message = String.format(
		           msg,
		            ticket.getTicketNumber(),
		            ticket.getCounter().getName()
		    );
		sendMessage(message,ticket);
		
	}

	@Override
	public void sendTicketTransferred(Ticket ticket) {
		
		String msg= """
	        Votre ticket %s
            a été transféré
            vers le guichet %s.
	            """;
		String message = String.format(
		           msg,
		            ticket.getTicketNumber(),
		            ticket.getCounter().getName()
		    );
		sendMessage(message,ticket);
	}

	@Override
	public void sendTicketCompleted(Ticket ticket) {

		String msg= """
	            Merci de votre visite.
				Votre ticket %s
				est terminé.
            """;
		String message = String.format(
		           msg,
		            ticket.getTicketNumber()
		    );
		sendMessage(message,ticket);
	}

	@Override
	public void sendTicketCancelled(Ticket ticket) {
		String msg= """
				
			Votre ticket %s

            a été annulé.
	            """;
		String message = String.format(
		           msg,
		            ticket.getTicketNumber()
		    );
		sendMessage(message,ticket);
		
	}

}
