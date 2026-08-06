package com.nsglobal.queue.bankservice.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	
	private BankService getById(Long id) {
		BankService serv=bankserviceRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Ce service est introuvable."));
		
		if(serv.getActive()==false) {
			throw new RuntimeException("Ce service n'est plus actif pour le moment.");
		}
		
		return serv;
	}
	
	@Override
	public BankServiceResponseDto create(BankServiceRequestDto bankService) {
		
		Branch branch = branchRepo.findById(bankService.getBranchId())
				.orElseThrow(() -> new RuntimeException("Succursale introuvable pour creer le service."));
		
		BankService bk = mapper.toEntity(bankService);
		
		bk.setBranch(branch);

		return mapper.toBrancResponseDto(bankserviceRepo.save(bk));
	}

	@Override
	public BankServiceResponseDto update(Long id, BankServiceRequestDto dto) {

		BankService existance = getById(id);
		BankService bankService = mapper.toEntity(dto);

		existance.setCode(bankService.getCode());
		existance.setActive(bankService.getActive());
		existance.setPrefix(bankService.getPrefix());
		existance.setName(bankService.getName());
		existance.setPriority(bankService.getPriority());
		existance.setEstimatedDurationMinutes(bankService.getEstimatedDurationMinutes());
		// existance.

		return mapper.toBrancResponseDto(bankserviceRepo.save(existance));
	}

	@Override
	public BankServiceResponseDto findById(Long id) {

		return mapper.toBrancResponseDto(getById(id));
	}

	@Override
	public Page<BankServiceResponseDto> findAll(Pageable page) {
		
		return bankserviceRepo.findAll(page)
				.map(mapper::toBrancResponseDto);
	}

	@Override
	public void delete(Long id) {
		bankserviceRepo.deleteById(id);

	}

}
