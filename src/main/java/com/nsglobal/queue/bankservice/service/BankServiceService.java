package com.nsglobal.queue.bankservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.common.response.ApiResponse;

public interface BankServiceService {

	ApiResponse<BankServiceResponseDto> create(BankServiceRequestDto bankService);

	ApiResponse<BankServiceResponseDto> update(Long id, BankServiceRequestDto bankService);

	ApiResponse<BankServiceResponseDto> findById(Long id);

	Page<BankServiceResponseDto> findAll(Pageable pape);
	
	ApiResponse<List<BankServiceResponseDto>> findAll();
	
	ApiResponse<BankServiceResponseDto> delete(Long id);
	
	ApiResponse<BankServiceResponseDto> changeActivateState(Long id,boolean state);
}