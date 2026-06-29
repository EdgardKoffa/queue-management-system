package com.nsglobal.queue.agency.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.entity.Agency;
import com.nsglobal.queue.agency.mapper.AgencyMapper;
import com.nsglobal.queue.agency.repository.AgencyRepository;
import com.nsglobal.queue.agency.service.AgencyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {
	
	private final AgencyRepository agencyRepository;
	
	private final AgencyMapper mapper;

	@Override
	public AgencyResponseDto create(AgencyRequestDto agency) {
		return mapper.toResponseDto(agencyRepository.save(mapper.toEntity(agency)));
	}

	 @Override
	    public AgencyResponseDto findById(Long id) {
	        return mapper.toResponseDto(agencyRepository.findById(id).orElseThrow());
	    }
	
	 @Override
	    public List<AgencyResponseDto> findAll() {
		 	
	        return mapper.toListResponseDto(agencyRepository.findAll());
	    }

	@Override
    public void delete(Long id) {
        agencyRepository.deleteById(id);
    }



	@Override
    public AgencyResponseDto update(Long id, AgencyRequestDto dto) {

        Agency existingAgency =agencyRepository.findById(id).orElseThrow();//findById(id);
        
        Agency agency=mapper.toEntity(dto);
        existingAgency.setCode(agency.getCode());
        existingAgency.setName(agency.getName());
        existingAgency.setPhone(agency.getPhone());
        existingAgency.setEmail(agency.getEmail());
        existingAgency.setStatus(agency.getStatus());

        return mapper.toResponseDto(agencyRepository.save(existingAgency));
    }

}
