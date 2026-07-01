package com.nsglobal.queue.ticket.entity;

import com.nsglobal.queue.common.entity.BaseEntity;
import com.nsglobal.queue.common.enums.TicketHistoryActions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistory extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id",nullable = false)
	private Ticket ticket;
	
	//private Emplyee employee;
	
	@Column
	private TicketHistoryActions action;
	
	@Column(nullable = true)
	private String comment;
	
}
