package com.nsglobal.queue.branch.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.common.enums.EnumStatus;
import com.nsglobal.queue.common.response.ApiResponse;

public interface BranchService {

	ApiResponse<BranchResponseDto> create(BranchRequestDto branch);

	ApiResponse<BranchResponseDto> update(Long id, BranchRequestDto branch);

	ApiResponse<BranchResponseDto> findById(Long id);

	Page<BranchResponseDto> findAll(Pageable page);
	
	ApiResponse<List<BranchResponseDto>> findAll();
	
	ApiResponse<BranchResponseDto>  changeStatus(EnumStatus status,Long id);

	ApiResponse<BranchResponseDto> delete(Long id);
}
