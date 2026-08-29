package com.nsglobal.queue.counter.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasPermissions;
import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
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
	
	@PreAuthorize(HasPermissions.HAS_COUNTER_OPEN)
	@PatchMapping("/{id}/open")
	public ResponseEntity<ApiResponse<CounterResponseDto>>  open(
			@Valid
			@PathVariable
			Long id
			) {
		return ResponseEntity.ok(counterervice.open(id));
	}
	
	@PreAuthorize(HasPermissions.HAS_COUNTER_CLOSE)
	@PatchMapping("/{id}/close")
	public ResponseEntity<ApiResponse<CounterResponseDto>> close(
			@Valid
			@PathVariable
			Long id
			) {
		return ResponseEntity.ok(counterervice.close(id));
	}
	
	@PatchMapping(HasPermissions.HAS_COUNTER_ASSIGN)
	@PostMapping("/{id}/assign/{operatorId}")
	public ResponseEntity<ApiResponse<CounterResponseDto>> assign(
			@Valid
			@PathVariable
			Long id,
			@Valid
			@PathVariable
			Long operatorId
			) {
		return ResponseEntity.ok(counterervice.assign(id,operatorId));
	}
	
	@PreAuthorize(HasPermissions.HAS_COUNTER_RELEASE)
	@PatchMapping("/{id}/release")
	public ResponseEntity<ApiResponse<CounterResponseDto>> busy(
			@Valid
			@PathVariable
			Long id
			) {
		return ResponseEntity.ok(counterervice.release(id));
	}
	
	//@PreAuthorize(HasPermissions.HAS_COUNTER_VIEW)
	@GetMapping
	public ResponseEntity<ApiPageResponse<CounterResponseDto>> findAll(
			@PageableDefault(page = 0,size = 10) 
			Pageable pageable) {
		return ResponseEntity.ok(counterervice.findAll(pageable));
	}
	
	@PreAuthorize(HasPermissions.HAS_COUNTER_VIEW)
	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<CounterResponseDto>>> findAll() {
		return ResponseEntity.ok(counterervice.findAll());
	}
	
	//@PreAuthorize(HasPermissions.HAS_COUNTER_VIEW)
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CounterResponseDto>> findById(@Valid @PathVariable Long id) {
		return ResponseEntity.ok(counterervice.findById(id));
	}
	
	//@PreAuthorize(HasPermissions.HAS_COUNTER_CREATE)
	@PostMapping
	public ResponseEntity<ApiResponse<CounterResponseDto>> create(@Valid @RequestBody CounterRequestDto counter) {
		return ResponseEntity.ok(counterervice.create(counter));
	}
	
	//@PreAuthorize(HasPermissions.HAS_COUNTER_UPDATE)
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CounterResponseDto>> update(@Valid @PathVariable Long id, @Valid @RequestBody CounterRequestDto counter) {
		return ResponseEntity.ok(counterervice.update(counter, id));
	}
	
	//@PreAuthorize(HasPermissions.HAS_COUNTER_DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<CounterResponseDto>> delete(@Valid @PathVariable Long id) {
		
		return ResponseEntity.ok(counterervice.delete(id));
	}
	
	//@PreAuthorize(HasPermissions.HAS_COUNTER_DELETE)
		@PatchMapping("/{id}/isactivate/{isactive}")
		public ResponseEntity<ApiResponse<CounterResponseDto>> isActivate(@Valid @PathVariable Long id,
				@Valid @PathVariable boolean isactive) {
			
			return ResponseEntity.ok(counterervice.activate_desactivateCounter(id,isactive));
		}
		
		//@PreAuthorize(HasPermissions.HAS_COUNTER_DELETE)
		@PatchMapping("/{id}/status/{status}")
				public ResponseEntity<ApiResponse<CounterResponseDto>> changeStatus(@Valid @PathVariable Long id,
						@Valid @PathVariable CounterStatus status) {
					
					return ResponseEntity.ok(counterervice.changeCounterStatus(id,status));
				}

}
