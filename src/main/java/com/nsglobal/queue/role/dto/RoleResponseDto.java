package com.nsglobal.queue.role.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleResponseDto {
	
	 private Long id;

	    private String name;

	    private String description;

	    private Set<PermissionDto> permissions;

}
