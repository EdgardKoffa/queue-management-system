package com.nsglobal.queue.bankservice.controller;

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

import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.bankservice.service.BankServiceService;
import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasPermissions;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.SERVICES)
@RequiredArgsConstructor
@Validated
public class BankServiceController {

	private final BankServiceService bankerviceervice;
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_BRANCHS)
	@GetMapping
	public ResponseEntity<ApiPageResponse<BankServiceResponseDto>> findAll(
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
        
        // 1. Appeler le service pour récupérer la page Spring Data
        Page<BankServiceResponseDto> userPage = bankerviceervice.findAll(pageable);
        
		return ResponseEntity.ok(
				ResponseBuilder.page("Liste des services...",userPage));
	}
	
	@PreAuthorize(HasPermissions.HAS_VIEW_DETAIL)
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BankServiceResponseDto>> findById(@Valid @PathVariable Long id) {
		return ResponseEntity.ok(bankerviceervice.findById(id));
	}
	
	@PreAuthorize(HasPermissions.HAS_VIEW_LIST)
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<BankServiceResponseDto>>> findAll() {
		return ResponseEntity.ok(bankerviceervice.findAll());
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_SERVICE)
	@PostMapping
	public ResponseEntity<ApiResponse<BankServiceResponseDto>> create(@Valid @RequestBody BankServiceRequestDto bankService) {
		return ResponseEntity.ok(bankerviceervice.create(bankService));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_SERVICE)
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BankServiceResponseDto>> update(@Valid @PathVariable Long id,
			@Valid @RequestBody BankServiceRequestDto bankService) {
		return ResponseEntity.ok(bankerviceervice.update(id, bankService));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_SERVICE)
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<BankServiceResponseDto>> delete(@Valid @PathVariable Long id) {
	return	ResponseEntity.ok(bankerviceervice.delete(id));
	}
	
	@PreAuthorize(HasPermissions.HAS_MANAGE_SERVICE)
	@PatchMapping("/{id}/isactive/{isactive}")
	public ResponseEntity<ApiResponse<BankServiceResponseDto>> chageState(
			@Valid @PathVariable Long id,
			@Valid @PathVariable boolean isactive) {
	return	ResponseEntity.ok(bankerviceervice.changeActivateState(id,isactive));
	}

}
