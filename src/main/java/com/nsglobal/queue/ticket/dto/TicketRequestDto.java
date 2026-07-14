package com.nsglobal.queue.ticket.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.nsglobal.queue.common.enums.TicketPriority;
import com.nsglobal.queue.common.enums.TicketStatus;

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
	private String phone;
	
	@Nullable
	private String email;
	
	@Nullable
	private String language;

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
