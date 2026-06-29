package com.nsglobal.queue.agency.service;

import java.util.List;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;


public interface AgencyService {
	
	  AgencyResponseDto create(AgencyRequestDto agency);

	  AgencyResponseDto update(Long id, AgencyRequestDto agency);

	  AgencyResponseDto findById(Long id);

	    List<AgencyResponseDto> findAll();

	    void delete(Long id);

}
