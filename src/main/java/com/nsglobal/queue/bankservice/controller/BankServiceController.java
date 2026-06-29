package com.nsglobal.queue.bankservice.controller;

import java.util.List;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.SERVICES)
@RequiredArgsConstructor
public class BankServiceController {
	
	private final BankServiceService bankerviceervice;
 @GetMapping
	public List<BankServiceResponseDto> findAll(){
		return bankerviceervice.findAll();
	}
	
	@GetMapping("/{id}")
	public BankServiceResponseDto findById(@Valid @PathVariable Long id) {
		return bankerviceervice.findById(id);
	}
	
		
@PostMapping
	public BankServiceResponseDto create(@Valid @RequestBody BankServiceRequestDto bankService) {
		return bankerviceervice.create(bankService);
	}
	
	@PutMapping("/{id}")
	public BankServiceResponseDto update(@Valid @PathVariable Long id,@Valid @RequestBody BankServiceRequestDto bankService) {
		return bankerviceervice.update(id, bankService);
	}
	
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		bankerviceervice.delete(id);
	}
 
}
