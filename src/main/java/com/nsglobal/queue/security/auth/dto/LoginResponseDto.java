package com.nsglobal.queue.security.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
	
	private String token;
	
	private String username;
	
	private String email;

	private String tokenType;

	private String firstname;

	private String lastname;

	private String role;
	
	 private Long branchId;
}
