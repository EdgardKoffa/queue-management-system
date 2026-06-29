package com.nsglobal.queue.counter.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.counter.mapper.CounterMapper;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.counter.service.CounterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {
	
	private CounterRepository counterRepo;
	private CounterMapper mapper;
	private BranchRepository branchRepo;

	@Override
	public CounterResponseDto create(CounterRequestDto dto) {
		
		Branch br=branchRepo.findById(dto.getBranchId()).orElseThrow(
				()-> new RuntimeException("Branche introuvable")
				);

		Counter counter=mapper.toEntity(dto);
		counter.setBranch(br);
		
		return mapper.toCounterResponse(counterRepo.save(counter));
	}

	@Override
	public CounterResponseDto update(CounterRequestDto dto, Long id) {
		
		Counter existanceC=counterRepo.findById(id).orElseThrow(
				()->new RuntimeException("Guichet introuvable")
				)
				;
		
		Counter counter=mapper.toEntity(dto);
		
		existanceC.setActive(counter.getActive());
		existanceC.setCode(counter.getCode());
		existanceC.setName(counter.getName());
		existanceC.setNumber(counter.getNumber());
		existanceC.setStatus(counter.getStatus());
		
		
		return mapper.toCounterResponse( counterRepo.save(existanceC));
	}

	@Override
	public CounterResponseDto findById(Long id) {
		return mapper.toCounterResponse( counterRepo.findById(id).orElseThrow());
	}

	@Override
	public List<CounterResponseDto> findAll() {
		return mapper.toListCounterResponseDto(counterRepo.findAll());
	}

	@Override
	public void delete(Long id) {
		counterRepo.deleteById(id);

	}

}
