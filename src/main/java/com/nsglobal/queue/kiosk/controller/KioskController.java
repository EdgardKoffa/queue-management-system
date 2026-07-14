package com.nsglobal.queue.kiosk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.kiosk.dto.TicketGenerationRequestDto;
import com.nsglobal.queue.kiosk.dto.TicketGenerationResponseDto;
import com.nsglobal.queue.kiosk.service.KioskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.KIOSK)
@RequiredArgsConstructor
@Validated
public class KioskController {
	
	 private final KioskService kioskService;

	    @PostMapping("/tickets")
	    public ResponseEntity<TicketGenerationResponseDto> createTicket(
	            @RequestBody 
	            @Valid 
	            TicketGenerationRequestDto request
	            ){

	        return ResponseEntity.ok(
	                kioskService.generateTicket(request)
	        );

	    }
}
