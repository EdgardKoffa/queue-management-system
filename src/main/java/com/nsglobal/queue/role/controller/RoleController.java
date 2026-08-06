package com.nsglobal.queue.role.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.nsglobal.queue.role.dto.RoleRequestDto;
import com.nsglobal.queue.role.dto.RoleResponseDto;
import com.nsglobal.queue.role.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.API_V1+"/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {
	private final RoleService service;
	
	@PostMapping
	public ResponseEntity<RoleResponseDto> add(
			@Valid
			@RequestBody
			RoleRequestDto dto){
		return ResponseEntity.ok(service.create(dto));
	}
	
	@GetMapping
	public ResponseEntity<List<RoleResponseDto>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoleResponseDto> findById(
			@Valid
			@PathVariable
			Long id
			){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RoleResponseDto> put(
			@Valid
			@PathVariable
			Long id,
			@Valid 
			@RequestBody
			RoleRequestDto dto
			){
		return ResponseEntity.ok(service.update(id,dto));
	}
	
	@DeleteMapping("/{id}")
	public void delete(
			@Valid
			@PathVariable
			Long id
			){
		service.delete(id);
	}
	
	@PatchMapping("/{id}/permission/{permissionId}")
	public ResponseEntity<RoleResponseDto> assignPermission(
			@Valid
			@PathVariable
			Long id,
			@Valid
			@PathVariable
			Long permissionId
			){
		return ResponseEntity.ok(service.assignPermission(id,permissionId));
	}
	
	@DeleteMapping("/{id}/permission/{permissionId}")
	public ResponseEntity<RoleResponseDto> removePermission(
			@Valid
			@PathVariable
			Long id,
			@Valid
			@PathVariable
			Long permissionId
			){
		return ResponseEntity.ok(service.removePermission(id,permissionId));
	}
}
