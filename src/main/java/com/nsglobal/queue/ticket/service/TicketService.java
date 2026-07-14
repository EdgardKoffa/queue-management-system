package com.nsglobal.queue.ticket.service;

import java.util.List;

import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;

public interface TicketService {
	public TicketResponseDto create(TicketRequestDto dto);

	public Ticket findById(Long id);

	public List<TicketResponseDto> findAll();

	public void remove(Long id);
	
	public void proccessingAbsentTicket(Long timeout);
	
	
}