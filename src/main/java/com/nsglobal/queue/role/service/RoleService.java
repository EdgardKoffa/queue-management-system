package com.nsglobal.queue.role.service;

import java.util.List;

import com.nsglobal.queue.role.dto.RoleRequestDto;
import com.nsglobal.queue.role.dto.RoleResponseDto;

public interface RoleService {
	 RoleResponseDto create(RoleRequestDto request);

	    RoleResponseDto update(Long id, RoleRequestDto request);

	    RoleResponseDto findById(Long id);

	    List<RoleResponseDto> findAll();

	    void delete(Long id);

	    RoleResponseDto assignPermission(Long roleId, Long permissionId);

	    RoleResponseDto removePermission(Long roleId, Long permissionId);
}
