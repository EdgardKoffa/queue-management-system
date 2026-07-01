package com.nsglobal.queue.ticket.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.TicketPriority;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "ticket")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends BaseEntity {
	
	@Column
	private String ticketNumber;
	
	@Column
	private Integer sequenceNumber;
	
	@Column
	private LocalDate ticketDate;
	
	@Column(name = "issue_time")
	private LocalDateTime issueTime;
	
	@Column(name = "call_time")
	private LocalDateTime callTime;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	@Column(name = "estimated_waiting",nullable = true)
	private Long estimatedWaiting;
	
	@Column(name = "notes",nullable = true)
	private String notes;
	
	@Column(name = "customer_name",nullable = true)
	private String customerName;

	@Column
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@Enumerated(EnumType.STRING)
	private TicketPriority priority;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false)
	private BankService service;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "counter_id", nullable = true)
	private Counter counter;

}
