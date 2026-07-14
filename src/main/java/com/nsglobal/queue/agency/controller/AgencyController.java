package com.nsglobal.queue.agency.controller;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.service.AgencyService;
import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasRoleNames;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class AgencyController {
	
	private final AgencyService agencyService;
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@GetMapping
	public List<AgencyResponseDto> findAll() {
		
		System.out.println("getting all agency");
		List<AgencyResponseDto> list=agencyService.findAll();
		System.out.println("List ------\n");
		System.out.println(list);
		return list;
		
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@GetMapping("/{id}")
	public AgencyResponseDto findById(@Valid @PathVariable Long id) {

		return agencyService.findById(id);
	}

	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping
	public AgencyResponseDto create(
			@Valid 
			@RequestBody 
			AgencyRequestDto agency) {

		return agencyService.create(agency);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PutMapping("/{id}")
	public AgencyResponseDto update(@Valid @PathVariable Long id, @Valid @RequestBody AgencyRequestDto agency) {

		return agencyService.update(id, agency);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		agencyService.delete(id);
	}
}
