package com.nsglobal.queue.ticket.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.nsglobal.queue.common.enums.TicketPriority;
import com.nsglobal.queue.common.enums.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
	
	private Long id;
	
	private String ticketNumber;

	private Integer sequenceNumber;

	private LocalDate ticketDate;

	private LocalDateTime issuedAt;
	
	
	private TicketStatus status;

	
	private String branchName;
	private Long branchId;
	
	private String counterName;
	private Long counterId;
	
	private Long serviceId;
	private String serviceName;
	
	 private TicketPriority priority;
	 
	 private LocalDateTime createdAt;
}
