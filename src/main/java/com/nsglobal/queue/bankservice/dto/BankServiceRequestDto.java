package com.nsglobal.queue.bankservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BankServiceRequestDto {
	
	@NotBlank
	@Size(max = 20)
	private  String code;
	
	@NotBlank
	@Size(max = 100)
	private  String name;
	
	@NotBlank
	private String prefix;
	
	@NotBlank
	private Integer priority;
	
	@NotBlank
	private Integer estimatedDurationMinutes;
	@NotNull
	private Boolean active;
	@NotBlank
	private Long branchId;
}
