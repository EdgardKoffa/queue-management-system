package com.nsglobal.queue.display.service;

import com.nsglobal.queue.display.dto.AnnouncementDto;
import com.nsglobal.queue.display.dto.CounterDisplayDto;
import com.nsglobal.queue.display.dto.WaittingScreenDto;

public interface DisplayService {
	
	 	WaittingScreenDto getWaitingScreen();

	    CounterDisplayDto getCounterDisplay(Long counterId);

	    AnnouncementDto getAnnouncement(Long ticketId);
}
