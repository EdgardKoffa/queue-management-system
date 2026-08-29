package com.nsglobal.queue.counter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;

public interface CounterService {

	ApiResponse<CounterResponseDto> create(CounterRequestDto counter);

	ApiResponse<CounterResponseDto> update(CounterRequestDto counter, Long id);

	ApiResponse<CounterResponseDto> findById(Long id);

	ApiResponse<List<CounterResponseDto>> findAll();
	
	ApiPageResponse<CounterResponseDto> findAll(Pageable pageable);

	ApiResponse<CounterResponseDto> delete(Long id);
	
	ApiResponse<CounterResponseDto> open(Long counterId);

	ApiResponse<CounterResponseDto> close(Long counterId);
	
	ApiResponse<CounterResponseDto> changeCounterStatus(Long counterId,CounterStatus status);
	
	ApiResponse<CounterResponseDto> activate_desactivateCounter(Long counterId,boolean isctivate);

	ApiResponse<CounterResponseDto> assign(Long counterId,Long operatorId);

	ApiResponse<CounterResponseDto> release(Long counterId);
	
	ApiResponse<List<CounterResponseDto>> openAllCounters();

	ApiResponse<List<CounterResponseDto>> closeAllCounters();
	//TicketResponseDto callNext(Long counterId);
}
