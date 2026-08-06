package com.nsglobal.queue.agency.controller;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.service.AgencyService;
import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasPermissions;
import com.nsglobal.queue.common.constant.HasRoleNames;
import com.nsglobal.queue.common.enums.EnumStatus;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@GetMapping
	public ResponseEntity<ApiPageResponse<AgencyResponseDto>> findAllPage(
			@PageableDefault(page = 0,size = 10)
			Pageable pageable) {

		Page<AgencyResponseDto> list=agencyService.findAll(pageable);
		System.out.println("findAll ===> "+list.getSize());
		return ResponseEntity.ok(
				ResponseBuilder.page(
						"Liste des agences recupérée.",
				list));
		
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<AgencyResponseDto>>> findAll() {

		ApiResponse<List<AgencyResponseDto>> list=agencyService.findAll();

		return ResponseEntity.ok(list);
		
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<AgencyResponseDto>> findById(@Valid @PathVariable Long id) {

		return ResponseEntity.ok(
						agencyService.findById(id));
	}

	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@PostMapping
	public ResponseEntity<ApiResponse<AgencyResponseDto>> create(
			@Valid 
			@RequestBody 
			AgencyRequestDto agency) {

		return ResponseEntity.ok(
						agencyService.create(agency));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<AgencyResponseDto>> update(@Valid @PathVariable Long id, @Valid @RequestBody AgencyRequestDto agency) {

		return ResponseEntity.ok(
						agencyService.update(id, agency));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@PatchMapping("/{id}/status/{status}")
	public ResponseEntity<ApiResponse<AgencyResponseDto>> patchStatus(
			@Valid @PathVariable Long id, 
			@Valid @PathVariable EnumStatus status) {

		return ResponseEntity.ok(agencyService.changeStatus(status,id));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_AGENCY)
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		agencyService.delete(id);
	}
}
