package com.nsglobal.queue.user.service;


import java.util.List;

import com.nsglobal.queue.user.dto.UserPatchResponseDto;
import com.nsglobal.queue.user.dto.UserRequestDto;
import com.nsglobal.queue.user.dto.UserResponseDto;

public interface UserService {
	
 public UserResponseDto create(UserRequestDto dto);
 
 public UserResponseDto update(UserRequestDto dto,Long id);
 
 public UserResponseDto findById(Long id);
 
 public UserResponseDto findByUserName( String userName);
 
 public UserPatchResponseDto enableDesableUser(Long userId,boolean isEnabled);
 
 public UserPatchResponseDto lockUnlockUserUser(Long userId,boolean isLocked);
 
 public UserPatchResponseDto assignRole(Long userId,Long roleId);
 
 public UserPatchResponseDto changeUserBranch(Long userId,Long branchId);
 
 public List<UserResponseDto> findAll();
 
// public List<UserResponseDto> findAllWithNotDeletedAt();
 
 public UserPatchResponseDto removeUser(Long id);
 
}
