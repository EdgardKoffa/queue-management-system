package com.nsglobal.queue.branch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.branch.service.BranchService;
import com.nsglobal.queue.common.constant.ApiRoutes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.BRANCHES)
@RequiredArgsConstructor
public class BranchController {
	
	private final BranchService branchService;
	
	@GetMapping
	public List<BranchResponseDto> findAll(){
		return branchService.findAll();
	}
	
	@GetMapping("/{id}")
	public BranchResponseDto findById(@Valid @PathVariable Long id) {
		return branchService.findById(id);
	}
	
	@PostMapping
	public BranchResponseDto create(@Valid @RequestBody BranchRequestDto branch) {
		return branchService.create(branch);
	}
	
	@PutMapping("/{id}")
	public BranchResponseDto update(@Valid @PathVariable Long id,@Valid @RequestBody BranchRequestDto branch) {
		return branchService.update(id, branch);
	}
	
	@DeleteMapping("/{id}")
	void delete(@Valid @PathVariable Long id) {
		branchService.delete(id);
	}
}
