package com.nsglobal.queue.role.service;

import java.util.List;

import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.role.dto.RoleRequestDto;
import com.nsglobal.queue.role.dto.RoleResponseDto;
import com.nsglobal.queue.role.entity.Permission;

public interface RoleService {
	ApiResponse<RoleResponseDto> create(RoleRequestDto request);

	    ApiResponse<RoleResponseDto> update(Long id, RoleRequestDto request);

	    ApiResponse<RoleResponseDto> findById(Long id);

	    ApiResponse<List<RoleResponseDto>> findAll();
	    
	    ApiResponse<List<Permission>> permissionFindAll();

	    ApiResponse<RoleResponseDto> delete(Long id);
	    

	    ApiResponse<RoleResponseDto> assignPermission(Long roleId, Long permissionId);

	    ApiResponse<RoleResponseDto> removePermission(Long roleId, Long permissionId);
}
