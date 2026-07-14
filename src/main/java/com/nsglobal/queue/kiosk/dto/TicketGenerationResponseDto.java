package com.nsglobal.queue.kiosk.dto;

import java.time.LocalDateTime;

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
public class TicketGenerationResponseDto {
	
	 private Long ticketId;

	    private String ticketNumber;

	    private String branch;

	    private String service;

	    private Integer estimatedWaitingTime;

	    private String qrCode;

	    private LocalDateTime issueTime;

}
