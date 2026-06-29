package com.nsglobal.queue.bankservice.service;
import java.util.List;

import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;

public interface BankServiceService {
	
	BankServiceResponseDto create(BankServiceRequestDto bankService);
	
	BankServiceResponseDto update(Long id,BankServiceRequestDto bankService);
	
	BankServiceResponseDto findById(Long id);
	
	List<BankServiceResponseDto> findAll();
	
	void delete(Long id);
}