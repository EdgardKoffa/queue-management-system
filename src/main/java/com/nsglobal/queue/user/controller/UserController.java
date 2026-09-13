package com.nsglobal.queue.user.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasPermissions;
import com.nsglobal.queue.common.constant.HasRoleNames;
import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
import com.nsglobal.queue.user.dto.UserPatchResponseDto;
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
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@PostMapping
		public ResponseEntity<ApiResponse<UserResponseDto>> createAccount(
				@Valid
				@RequestBody
				UserRequestDto dto
				) {
			return ResponseEntity.ok(userService.create(dto));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@PutMapping("/{id}")
		public ResponseEntity<ApiResponse<UserResponseDto>> updateAccount(
				@Valid 
				@PathVariable
				Long id,
				@Valid
				@RequestBody
				UserRequestDto dto) {
			
			return ResponseEntity.ok(userService.update(dto, id));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@DeleteMapping("/{id}")
		public ResponseEntity<ApiResponse<UserResponseDto>> removeAccount(
				@Valid 
				@PathVariable
				Long id) {
			
			return ResponseEntity.ok(userService.removeUser(id));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@GetMapping("/all")
		public ResponseEntity<ApiResponse<List<UserResponseDto>>> findAll() {
			return ResponseEntity.ok(userService.findAll());
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@GetMapping
		public ResponseEntity<ApiPageResponse<UserResponseDto>> findAll(
				@PageableDefault(page = 0, size = 10) Pageable pageable) {
	        
	        // 1. Appeler le service pour récupérer la page Spring Data
			ApiPageResponse<UserResponseDto> userPage = userService.findAll(pageable);
	        
			return ResponseEntity.ok(userPage);
		}
		
		@PreAuthorize(HasPermissions.HAS_VIEW_DETAIL)
		@GetMapping("/{id}")
		public ResponseEntity<ApiResponse<UserResponseDto>> findById(
				@Valid 
				@PathVariable
				Long id) {
			return ResponseEntity.ok(userService.findById(id));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@PatchMapping("/{id}/branch/{branchId}")
		public ResponseEntity<ApiResponse<UserResponseDto>> changeBranch(
				@Valid
				@PathVariable
				Long id,
				@Valid
				@PathVariable
				Long branchId
				){
			return ResponseEntity.ok(userService.changeUserBranch(id, branchId));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@PatchMapping("/{id}/role/{roleId}")
		public ResponseEntity<ApiResponse<UserResponseDto>> changeRole(
				@Valid
				@PathVariable
				Long id,
				@Valid
				@PathVariable
				Long roleId
				){
			return ResponseEntity.ok(userService.assignRole(id, roleId));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@PatchMapping("/{id}/status")
		public ResponseEntity<ApiResponse<UserResponseDto>> enableDesableUser(
				@Valid
				@PathVariable
				Long id,
				@Valid
				@RequestParam
				boolean isEnabled
				){
			return ResponseEntity.ok(userService.enableDesableUser(id, isEnabled));
		}
		
		@PreAuthorize(HasPermissions.HAS_MANAGE_USERS)
		@PatchMapping("/{id}/state")
		public ResponseEntity<ApiResponse<UserResponseDto>> lockUnlockUser(
				@Valid
				@PathVariable
				Long id,
				@Valid
				@RequestParam
				boolean islocked
				){
			return ResponseEntity.ok(userService.lockUnlockUserUser(id, islocked));
		}
		
}
