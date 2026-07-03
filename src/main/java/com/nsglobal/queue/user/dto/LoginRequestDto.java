package com.nsglobal.queue.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginRequestDto {
	
	@NotBlank
	private String userName;
	
	@NotBlank
	@NotEmpty
	private String password;
}
