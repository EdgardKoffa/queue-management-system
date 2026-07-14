package com.nsglobal.queue.kiosk.service;

import com.nsglobal.queue.kiosk.dto.TicketGenerationRequestDto;
import com.nsglobal.queue.kiosk.dto.TicketGenerationResponseDto;

public interface KioskService {
	TicketGenerationResponseDto generateTicket(
            TicketGenerationRequestDto request);
}
