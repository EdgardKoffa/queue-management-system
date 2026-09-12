package com.nsglobal.queue.user.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.common.response.ApiPageResponse;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.user.dto.UserRequestDto;
import com.nsglobal.queue.user.dto.UserResponseDto;

public interface UserService {
	
 public ApiResponse<UserResponseDto> create(UserRequestDto dto);
 
 public ApiResponse<UserResponseDto> update(UserRequestDto dto,Long id);
 
 public ApiResponse<UserResponseDto> findById(Long id);
 
 public ApiResponse<UserResponseDto> findByUserName( String userName);
 
 public ApiResponse<UserResponseDto> enableDesableUser(Long userId,boolean isEnabled);
 
 public ApiResponse<UserResponseDto> lockUnlockUserUser(Long userId,boolean isLocked);
 
 public ApiResponse<UserResponseDto> assignRole(Long userId,Long roleId);
 
 public ApiResponse<UserResponseDto> changeUserBranch(Long userId,Long branchId);
 
 public ApiResponse<List<UserResponseDto>> findAll();
 
 public ApiPageResponse<UserResponseDto> findAll(Pageable pageable);
 
// public List<UserResponseDto> findAllWithNotDeletedAt();
 
 public ApiResponse<UserResponseDto> removeUser(Long id);
 
}
