package com.nsglobal.queue.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.nsglobal.queue.user.dto.UserRequestDto;
import com.nsglobal.queue.user.dto.UserResponseDto;
import com.nsglobal.queue.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.USERS)
@Validated
@RequiredArgsConstructor
public class UserController {

		private final UserService userService;
		
		@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
		@PostMapping
		public ResponseEntity<UserResponseDto> createAccount(
				@Valid
				@RequestBody
				UserRequestDto dto
				) {
			return ResponseEntity.ok(userService.create(dto));
		}
		
		@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
		@PutMapping("/{id}")
		public ResponseEntity<UserResponseDto> updateAccount(
				@Valid 
				@PathVariable
				Long id,
				@Valid
				@RequestBody
				UserRequestDto dto) {
			
			return ResponseEntity.ok(userService.update(dto, id));
		}
		
		@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
		@DeleteMapping("/{id}")
		public void removeAccount(
				@Valid 
				@PathVariable
				Long id) {
			userService.removeUser(id);
		}
		
		@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
		@GetMapping
		public ResponseEntity<List<UserResponseDto>> findAll() {
			return ResponseEntity.ok(userService.findAll());
		}
		
		@PreAuthorize(HasRoleNames.HAS_SUPER_ADMIN)
		@GetMapping("/{id}")
		public ResponseEntity<UserResponseDto> findById(
				@Valid 
				@PathVariable
				Long id) {
			return ResponseEntity.ok(userService.findById(id));
		}
}
