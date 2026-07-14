package com.nsglobal.queue.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
	
	private String username;
	
	private String email;
	
	private String lastName;
	
	private String firstName;
	
	private String phone; 
	
	private boolean enabled;
	
	private boolean locked;
	
	private Long branch_id;
	
	private Long role_id;
	
	private String branch;
	
	private String role;
}
