package com.nsglobal.queue.branch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BranchRequestDto {
	
	 @NotBlank
	 @Size(max = 20)
	private String code;
	 
	 @NotBlank
	 @Size(max = 100)
	private String name;
	 
	 @NotBlank
	 @Size(max = 100)
	private String city;
	 
	 @NotBlank
	  @Size(max = 20)
	private String phone;
	 
	 @Email
	private String email;
	 
	@NotBlank
	private String address; 
	
	@NotNull
	private String status;
	
	@NotNull
	private  Long agencyId;
}
