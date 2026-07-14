package com.nsglobal.queue.ticket.engine;

import com.nsglobal.queue.ticket.dto.TicketResponseDto;

public interface QueueEngine {
	/**
	 * Appel d'un ticket dans la file par le guichet
	 * */
	TicketResponseDto callNextTicket(Long counterId);
	
	/**
	 * finir le ticket courant par le guichet
	 * */
	TicketResponseDto finishTicket(Long counterId);
	
	/**
	 * transfert d'un ticket vers un autre guichet
	 * */
	TicketResponseDto transferTicket(Long curentCounterId,Long destinationCounterId);
	
	/**
	 * annuler le ticket courant par le guichet
	 * */
	TicketResponseDto cancelTicket(Long counterId);
}
