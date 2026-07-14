package com.nsglobal.queue.security.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	@NotBlank(message = "Le nom d'utilisateur est obligatoire")
	private String userName;
	
	@NotBlank(message = "Le mot de passe est obligatoire")
	@NotEmpty
	private String password;
}
