package com.nsglobal.queue.bankservice.dto;

import java.time.LocalDateTime;

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
public class BankServiceResponseDto {
	
	private Long id;
	
	private  String code;
	
	private  String name;
	
	private String prefix;
	
	private Integer priority;

	private Integer estimatedDurationMinutes;

	private Boolean active;
	
	private Long branchId;
	
	private String branchName;
	
	private LocalDateTime createdAt;
}
