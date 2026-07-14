package com.nsglobal.queue.user.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nsglobal.queue.user.dto.UserRequestDto;
import com.nsglobal.queue.user.dto.UserResponseDto;
import com.nsglobal.queue.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(target = "role",ignore = true)
	@Mapping(target = "branch",ignore = true)
	@Mapping(target = "lastLogin",ignore = true)
	public	User toEntity(UserRequestDto dto);
	
	@Mapping(target = "role",source = "role.name")
	@Mapping(target = "branch",source = "branch.name")
	@Mapping(target = "role_id",source = "role.id")
	@Mapping(target = "branch_id",source = "branch.id")
	@Mapping(target = "username",source = "user.userName")
	public UserResponseDto toUserResponseDto(User user);
	
	@Mapping(target = "role",source = "role.name")
	@Mapping(target = "branch",source = "branch.name")
	@Mapping(target = "role_id",source = "role.id")
	@Mapping(target = "branch_id",source = "branch.id")
	@Mapping(target = "username",source = "user.userName")
	public List<UserResponseDto> toListUserResponseDto(List<User> users);
}
