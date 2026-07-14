package com.nsglobal.queue.user.service;


import java.util.List;

import com.nsglobal.queue.user.dto.UserRequestDto;
import com.nsglobal.queue.user.dto.UserResponseDto;

public interface UserService {
	
 public UserResponseDto create(UserRequestDto dto);
 
 public UserResponseDto update(UserRequestDto dto,Long id);
 
 public UserResponseDto findById(Long id);
 
 public UserResponseDto findByUserName( String userName);
 
 public List<UserResponseDto> findAll();
 
// public List<UserResponseDto> findAllWithNotDeletedAt();
 
 void removeUser(Long id);
 
}
