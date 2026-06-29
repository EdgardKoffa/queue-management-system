package com.nsglobal.queue.branch.dto;

import java.time.LocalDateTime;

import com.nsglobal.queue.agency.dto.AgencyResponseDto;

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

public class BranchResponseDto {
	private Long id;
	private String code;
	private String name;
	private String city;
	private String phone;
	private String email;
	private String address; 
	private String status;
	private Long agencyId;
    private String agencyName;
	private LocalDateTime createdAt;
	
}
