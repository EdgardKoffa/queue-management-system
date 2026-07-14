package com.nsglobal.queue.kiosk.dto;

import com.nsglobal.queue.common.enums.TicketPriority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketGenerationRequestDto {
	
	 private Long branchId;

	    private Long serviceId;

	    private TicketPriority priority;

	    private String language;

	    private String phone;
	    
	    private String email;
}
