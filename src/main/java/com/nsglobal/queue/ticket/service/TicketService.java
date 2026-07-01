package com.nsglobal.queue.ticket.service;

import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;

public interface TicketService {
	public TicketResponseDto create(TicketRequestDto dto);
	public TicketResponseDto callNextTicket(Long counterId);
}