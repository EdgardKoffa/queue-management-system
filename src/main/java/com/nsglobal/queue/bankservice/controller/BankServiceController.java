package com.nsglobal.queue.bankservice.controller;

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

import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.bankservice.service.BankServiceService;
import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasRoleNames;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.SERVICES)
@RequiredArgsConstructor
@Validated
public class BankServiceController {

	private final BankServiceService bankerviceervice;
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@GetMapping
	public List<BankServiceResponseDto> findAll() {
		return bankerviceervice.findAll();
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@GetMapping("/{id}")
	public BankServiceResponseDto findById(@Valid @PathVariable Long id) {
		return bankerviceervice.findById(id);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping
	public BankServiceResponseDto create(@Valid @RequestBody BankServiceRequestDto bankService) {
		return bankerviceervice.create(bankService);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PutMapping("/{id}")
	public BankServiceResponseDto update(@Valid @PathVariable Long id,
			@Valid @RequestBody BankServiceRequestDto bankService) {
		return bankerviceervice.update(id, bankService);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		bankerviceervice.delete(id);
	}

}
