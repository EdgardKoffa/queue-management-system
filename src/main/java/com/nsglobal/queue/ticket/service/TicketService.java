package com.nsglobal.queue.ticket.service;

import java.util.List;

import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;

public interface TicketService {
	public TicketResponseDto create(TicketRequestDto dto);

	public TicketResponseDto findById(Long id);

	public List<TicketResponseDto> findAll();

	public void remove(Long id);
}