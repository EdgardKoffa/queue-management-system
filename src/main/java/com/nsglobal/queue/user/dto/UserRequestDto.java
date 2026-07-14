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
public class UserRequestDto {
	
	@NotBlank(message = "Le nom d'utilisateur est obligatoire")
	private String userName;
	
	@NotBlank
	@NotEmpty
	private String password;
	
	@NotBlank(message = "L'adresse e-mail est obligatoire")
	private String email;
	
	@NotBlank(message = "Le nom est obligatoire")
	private String lastName;
	
	@NotBlank(message = "Le prénom est obligatoire")
	private String firstName;
	
	@NotBlank(message = "Le N° de téléphone est obligatoire")
	private String phone; 
	
	
	private boolean enabled;
	
	private boolean locked;
	
	@NotBlank(message = "L'utilisateur n'est rattaché à aucun succursale de la banque")
	private Long branch_id;
	
	@NotBlank(message = "L'utilisateur doit avoir un rôle")
	private Long role_id;
}
