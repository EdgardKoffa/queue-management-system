package com.nsglobal.queue.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasRoleNames;
import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.engine.QueueEngine;
import com.nsglobal.queue.ticket.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.TICKETS)
@RequiredArgsConstructor
@Validated
public class TicketController {
	
	private final TicketService ticketService;
	
	private final QueueEngine engine;
	
	@PreAuthorize(HasRoleNames.HAS_DISPLAY)
	@PostMapping("/create")
	public ResponseEntity<TicketResponseDto> create(
			@Valid @RequestBody
			TicketRequestDto dto
			) {
		
		return ResponseEntity.ok(ticketService.create(dto));
	}
	
	@PreAuthorize(HasRoleNames.HAS_OPERATOR)
	@GetMapping("/{counterId}/call-next")
	public ResponseEntity<TicketResponseDto> callNextTicket(
			@Valid 
			@PathVariable 
			Long counterId) {
		return ResponseEntity.ok(engine.callNextTicket(counterId));
	}
	
	@PreAuthorize(HasRoleNames.HAS_OPERATOR)
	@GetMapping("/{counterId}/finish")
	public ResponseEntity<TicketResponseDto> finishTicket(
			@Valid 
			@PathVariable 
			Long counterId) {
		return ResponseEntity.ok(engine.finishTicket(counterId));
	}
	
	@PreAuthorize(HasRoleNames.HAS_OPERATOR)
	@GetMapping("/{counterId}/cancel")
	public ResponseEntity<TicketResponseDto> cancelTicket(
			@Valid 
			@PathVariable 
			Long counterId) {
		return ResponseEntity.ok(engine.cancelTicket(counterId));
	}
	
	@PreAuthorize(HasRoleNames.HAS_OPERATOR)
	@GetMapping("/{counterId}/transfer/{destCounterId}")
	public ResponseEntity<TicketResponseDto> transferTicket(
			@Valid 
			@PathVariable 
			Long counterId,
			@Valid 
			@PathVariable 
			Long destCounterId
			) {
		return ResponseEntity.ok(engine.transferTicket(counterId,destCounterId));
	}
	

}
