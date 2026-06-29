package com.nsglobal.queue.counter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;
import com.nsglobal.queue.counter.service.CounterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.COUNTERS)
@RequiredArgsConstructor
public class CounterController {
	 private final CounterService counterervice;
	 
	 
	  @GetMapping
	public List<CounterResponseDto> findAll(){
		return counterervice.findAll();
	}
	
	@GetMapping("/{id}")
	public CounterResponseDto findById(@Valid @PathVariable Long id) {
		return counterervice.findById(id);
	}

	@PostMapping
	public CounterResponseDto create(@Valid @RequestBody CounterRequestDto counter) {
		return counterervice.create(counter);
	}
	
	@PutMapping("/{id}")
	public CounterResponseDto update(@Valid @PathVariable Long id,@Valid @RequestBody CounterRequestDto counter) {
		return counterervice.update(counter,id);
	}
	
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		counterervice.delete(id);
	}
	 
}
