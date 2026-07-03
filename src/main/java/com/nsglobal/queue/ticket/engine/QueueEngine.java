package com.nsglobal.queue.ticket.engine;

import com.nsglobal.queue.ticket.dto.TicketResponseDto;

public interface QueueEngine {
	TicketResponseDto callNextTicket(Long counterId);
}
