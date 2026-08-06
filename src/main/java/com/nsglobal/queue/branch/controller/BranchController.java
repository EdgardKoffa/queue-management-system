package com.nsglobal.queue.branch.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.branch.service.BranchService;
import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasPermissions;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.BRANCHES)
@RequiredArgsConstructor
@Validated
public class BranchController {

	private final BranchService branchService;
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_BRANCHS)
	@GetMapping
	public ResponseEntity<ApiPageResponse<BranchResponseDto>> findAll(
			@PageableDefault(page = 0, size = 10) 
			Pageable pageable) {
		Page<BranchResponseDto> list=branchService.findAll(pageable);
		
		ResponseEntity<ApiPageResponse<BranchResponseDto>> response=ResponseEntity.ok(
				ResponseBuilder
				.page("Liste des agences recupérées...",list));
		
		//System.out.println("ApiPageResponse<T> success"+list+"\n"+response);
		return response;
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_BRANCHS)
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BranchResponseDto>> findById(
			@Valid @PathVariable Long id) {
		return  ResponseEntity.ok(
				branchService.findById(id)
				);
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_BRANCHS)
	@PostMapping
	public ResponseEntity<ApiResponse<BranchResponseDto>> create(@Valid @RequestBody BranchRequestDto branch) {
		return ResponseEntity.ok(
				branchService.create(branch)
				);
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_BRANCHS)
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BranchResponseDto>> update(
			@Valid @PathVariable Long id, 
			@Valid @RequestBody BranchRequestDto branch) {
		return ResponseEntity.ok(branchService.update(id, branch));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_BRANCHS)
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<BranchResponseDto>> delete(@Valid @PathVariable Long id) {
		
		
		return  ResponseEntity.ok(branchService.delete(id));
	}
}
