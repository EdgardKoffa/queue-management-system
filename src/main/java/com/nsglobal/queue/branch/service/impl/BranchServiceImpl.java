package com.nsglobal.queue.branch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.agency.entity.Agency;
import com.nsglobal.queue.agency.repository.AgencyRepository;
import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.mapper.BranchMapper;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.branch.service.BranchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
	
	private final BranchRepository brancRepo;
	private final BranchMapper mapper;
	private final AgencyRepository agencyRepo;

	@Override
	public BranchResponseDto create(BranchRequestDto branch) {
		Agency agency=agencyRepo.findById(branch.getAgencyId()).orElseThrow(() -> new RuntimeException("Agence introuvable pour creer une branche."));
		Branch b=mapper.toEntity(branch);
		b.setAgency(agency);
		return mapper.toBrancResponseDto(brancRepo.save(b));
	}

	@Override
	public BranchResponseDto update(Long id, BranchRequestDto dto) {
		
		Branch existanceBranch=brancRepo.findById(id).orElseThrow(() -> new RuntimeException("Cette branche de l'agence introuvable"));
		Branch branch=mapper.toEntity(dto);
		
		existanceBranch.setCity(branch.getCity());
		existanceBranch.setAddress(branch.getAddress());
		existanceBranch.setAgency(branch.getAgency());
		existanceBranch.setEmail(branch.getEmail());
		existanceBranch.setPhone(branch.getPhone());
		existanceBranch.setName(branch.getName());
		existanceBranch.setStatus(branch.getStatus());
		existanceBranch.setCode(branch.getCode());
		
		return mapper.toBrancResponseDto(brancRepo.save(existanceBranch));
	}

	@Override
	public BranchResponseDto findById(Long id) {

		return mapper.toBrancResponseDto( brancRepo.findById(id).orElseThrow());
	}

	@Override
	public List<BranchResponseDto> findAll() {

		return mapper.toListBranchResponses(brancRepo.findAll());
	}

	@Override
	public void delete(Long id) {
		brancRepo.deleteById(id);

	}

}
