package com.nsglobal.queue.counter.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasRoleNames;
import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;
import com.nsglobal.queue.counter.service.CounterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.COUNTERS)
@RequiredArgsConstructor
@Validated
public class CounterController {
	
	private final CounterService counterervice;
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping("/{id}/open")
	public CounterResponseDto open(
			@Valid
			@PathVariable
			Long id
			) {
		return counterervice.open(id);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping("/{id}/close")
	public CounterResponseDto close(
			@Valid
			@PathVariable
			Long id
			) {
		return counterervice.close(id);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping("/{id}/assign/{operatorId}")
	public CounterResponseDto assign(
			@Valid
			@PathVariable
			Long id,
			@Valid
			@PathVariable
			Long operatorId
			) {
		return counterervice.assign(id,operatorId);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping("/{id}/release")
	public CounterResponseDto busy(
			@Valid
			@PathVariable
			Long id
			) {
		return counterervice.release(id);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@GetMapping
	public List<CounterResponseDto> findAll() {
		return counterervice.findAll();
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@GetMapping("/{id}")
	public CounterResponseDto findById(@Valid @PathVariable Long id) {
		return counterervice.findById(id);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PostMapping
	public CounterResponseDto create(@Valid @RequestBody CounterRequestDto counter) {
		return counterervice.create(counter);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@PutMapping("/{id}")
	public CounterResponseDto update(@Valid @PathVariable Long id, @Valid @RequestBody CounterRequestDto counter) {
		return counterervice.update(counter, id);
	}
	
	@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		counterervice.delete(id);
	}

}
