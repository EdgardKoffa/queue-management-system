package com.nsglobal.queue.role.dto;

import java.util.Set;

import com.nsglobal.queue.common.enums.EnumRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleRequestDto {
	
	@NotBlank
	private EnumRole name;
	
	private String description;
	
	private Set<Long> permissionIds;
}
