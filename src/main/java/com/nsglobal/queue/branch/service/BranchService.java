package com.nsglobal.queue.branch.service;

import java.util.List;

import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
public interface BranchService {
	
	BranchResponseDto create(BranchRequestDto branch);
	
	BranchResponseDto update(Long id,BranchRequestDto branch);
	
	BranchResponseDto findById(Long id);
	
	List<BranchResponseDto> findAll();
	
	void delete(Long id);
}
