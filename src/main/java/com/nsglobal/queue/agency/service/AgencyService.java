package com.nsglobal.queue.agency.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.common.enums.EnumStatus;
import com.nsglobal.queue.common.response.ApiResponse;

public interface AgencyService {

	ApiResponse<AgencyResponseDto> create(AgencyRequestDto agency);

	ApiResponse<AgencyResponseDto> update(Long id, AgencyRequestDto agency);

	ApiResponse<AgencyResponseDto> findById(Long id);

	Page<AgencyResponseDto> findAll(Pageable pageable);
	
	ApiResponse<List<AgencyResponseDto>> findAll();
	
	ApiResponse<AgencyResponseDto> changeStatus(EnumStatus status,Long id);

	void delete(Long id);

}
