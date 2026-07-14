package com.nsglobal.queue.display.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.display.dto.AnnouncementDto;
import com.nsglobal.queue.display.dto.CounterDisplayDto;
import com.nsglobal.queue.display.dto.WaittingScreenDto;
import com.nsglobal.queue.display.service.DisplayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.DISPLAY)
@RequiredArgsConstructor
public class DisplayController {
	
	private final DisplayService service;
	
	@GetMapping("/waitting")
	public ResponseEntity<WaittingScreenDto> getWaittingTicketScreen(){
		
		return ResponseEntity.ok(service.getWaitingScreen());
	}
	
	@GetMapping("/counters/{counterId}")
	public ResponseEntity<CounterDisplayDto> getCounterDisplayScreen(
			@PathVariable
			Long counterId) {
		
		return ResponseEntity.ok(service.getCounterDisplay(counterId));
	}
	
	@GetMapping("/announcement/{ticketId}")
	public ResponseEntity<AnnouncementDto> getMessageDisplay(
			@PathVariable
			Long ticketId) {
		
		return ResponseEntity.ok(service.getAnnouncement(ticketId));
	}
}
