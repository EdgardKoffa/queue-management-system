package com.nsglobal.queue.role.mapper;


import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.nsglobal.queue.role.dto.PermissionDto;
import com.nsglobal.queue.role.dto.RoleResponseDto;
import com.nsglobal.queue.role.entity.Permission;
import com.nsglobal.queue.role.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	
	 PermissionDto toPermissionDto(Permission permission);

	    Set<PermissionDto> toPermissionDtos(Set<Permission> permissions);

	    RoleResponseDto toResponse(Role role);

	    List<RoleResponseDto> toResponses(List<Role> roles);
}
