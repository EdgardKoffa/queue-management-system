package com.nsglobal.queue.bankservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;

public interface BankServiceService {

	BankServiceResponseDto create(BankServiceRequestDto bankService);

	BankServiceResponseDto update(Long id, BankServiceRequestDto bankService);

	BankServiceResponseDto findById(Long id);

	Page<BankServiceResponseDto> findAll(Pageable pape);

	void delete(Long id);
}