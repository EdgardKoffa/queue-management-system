package com.nsglobal.queue.counter.service;

import java.util.List;

import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;

public interface CounterService {
	
	CounterResponseDto create(CounterRequestDto counter);
	
	CounterResponseDto update(CounterRequestDto counter,Long id);
	
	CounterResponseDto findById(Long id);
	
	List<CounterResponseDto> findAll();
	void delete(Long id);
}
