package com.nsglobal.queue.agency.controller;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.service.AgencyService;
import com.nsglobal.queue.common.constant.ApiRoutes;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.AGENCIES)
@RequiredArgsConstructor
public class AgencyController {
	private final AgencyService agencyService;
	
	@GetMapping
	public List<AgencyResponseDto> findAll() {
		return agencyService.findAll();
	}
	
	@GetMapping("/{id}")
	public AgencyResponseDto findById(@Valid @PathVariable Long id) {
		
		return agencyService.findById(id);
	}
	
	@PostMapping
	public AgencyResponseDto create(@Valid @RequestBody AgencyRequestDto agency) {
		
		return agencyService.create(agency);
	}
	
	@PutMapping("/{id}")
	public AgencyResponseDto update(@Valid @PathVariable Long id,@Valid @RequestBody AgencyRequestDto agency) {
		
		return agencyService.update(id, agency);
	}
	
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id){
		agencyService.delete(id);
	}
}
