package com.nsglobal.queue.role.dto;

import com.nsglobal.queue.common.enums.EnumPermissions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionDto {
	
	private Long id;
	
	private EnumPermissions name;
	
	private String description;
}
