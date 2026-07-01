package com.nsglobal.queue.ticket.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.enums.TicketPriority;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.ticket.entity.Ticket;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
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
public class TicketRequestDto {
	
	@Nullable
	private String ticketNumber;

	@Nullable
	private Integer sequenceNumber;

	@Nullable
	private LocalDate ticketDate;
	
	@Nullable
	private LocalDateTime issuedAt;
	
	@Nullable
	private TicketStatus status;

	
	@NotNull
	 private Long branchId;
	
		@NotNull
	    private Long serviceId;
		
		@NotNull
	    private TicketPriority priority;
		
		@Nullable
		private Long counterId;
		

}
