package com.nsglobal.queue.agency.dto;

import java.time.LocalDateTime;

import com.nsglobal.queue.common.enums.EnumStatus;

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
public class AgencyResponseDto {
	  private Long id;

	    private String code;

	    private String name;

	    private String phone;

	    private String email;

	    private EnumStatus status;

	    private LocalDateTime createdAt;

}
