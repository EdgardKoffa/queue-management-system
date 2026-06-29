package com.nsglobal.queue.bankservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.agency.repository.AgencyRepository;
import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.bankservice.mapper.BankServiceMapper;
import com.nsglobal.queue.bankservice.repository.BankServiceRepository;
import com.nsglobal.queue.bankservice.service.BankServiceService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceServiceImpl implements BankServiceService {
	
	private final BankServiceRepository bankserviceRepo;
	private final BankServiceMapper mapper;
	private final BranchRepository branchRepo;

	@Override
	public BankServiceResponseDto create(BankServiceRequestDto bankService) {
		Branch branch=branchRepo.findById(bankService.getBranchId()).orElseThrow(()->new RuntimeException("Branche introuvable pour creer le service."));
		BankService bk=mapper.toEntity(bankService);
		bk.setBranch(branch);
		
		return mapper.toBrancResponseDto(bankserviceRepo.save(bk));
	}

	@Override
	public BankServiceResponseDto update(Long id, BankServiceRequestDto dto) {
		
		BankService existance=bankserviceRepo.findById(id).orElseThrow(
				()->new RuntimeException("Ce service est introuvable.")
				);
		BankService bankService=mapper.toEntity(dto);
				
		existance.setCode(bankService.getCode());
		existance.setActive(bankService.getActive());
		existance.setPrefix(bankService.getPrefix());
		existance.setName(bankService.getName());
		existance.setPriority(bankService.getPriority());
		existance.setEstimatedDurationMinutes(bankService.getEstimatedDurationMinutes());
		//existance.
		
		return mapper.toBrancResponseDto(bankserviceRepo.save(existance));
	}

	@Override
	public BankServiceResponseDto findById(Long id) {

		return mapper.toBrancResponseDto(bankserviceRepo.findById(id).orElseThrow());
	}

	@Override
	public List<BankServiceResponseDto> findAll() {

		return mapper.toListBranchResponses(bankserviceRepo.findAll());
	}

	@Override
	public void delete(Long id) {
		bankserviceRepo.deleteById(id);

	}

}
