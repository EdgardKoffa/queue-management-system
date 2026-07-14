package com.nsglobal.queue.appointment.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.appointment.service.AppointmentService;
import com.nsglobal.queue.common.constant.ApiRoutes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.API_V1+"/appointments")
@Validated
public class AppointementController {
	private final AppointmentService appointmentService;
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AppointmentResponseDto> create(
	        @Valid
	        @RequestBody
	        AppointmentRequestDto request){

	    return ResponseEntity.ok(appointmentService.create(request));

	}
	
	@GetMapping
	public ResponseEntity<List<AppointmentResponseDto>> findAll(){

	    return ResponseEntity.ok(appointmentService.findAll());

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AppointmentResponseDto> findById(
	        @PathVariable
	        Long id){

	    return ResponseEntity.ok(appointmentService.findById(id));

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AppointmentResponseDto> update(
	        @PathVariable
	        Long id,
	        @Valid
	        @RequestBody
	        AppointmentRequestDto request){

	    return ResponseEntity.ok(appointmentService.update(id, request));

	}
	
	@DeleteMapping("/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(
	        @PathVariable
	        Long id){
	    appointmentService.cancel(id);
	}
	
	@PostMapping("/check-in")
	public ResponseEntity<AppointmentResponseDto> checkIn(
	        @RequestParam
	        String qrCode){
	    return ResponseEntity.ok(appointmentService.checkIn(qrCode));

	}
	@GetMapping("/availability")
	public ResponseEntity<List<AppointmentAvailabilityDto>> getAvailability(
	        @RequestParam
	        Long branchId,
	        @RequestParam
	        Long serviceId,
	        @RequestParam
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	        LocalDate date){

	    return ResponseEntity.ok(appointmentService.getAvailableSlots(
	            branchId,
	            serviceId,
	            date));

	}
}
