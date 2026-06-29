package com.nsglobal.queue.ticket.entity;

import java.time.LocalDate;

import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
	    name = "ticket_sequence",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {
	            "branch_id",
	            "service_id",
	            "sequence_date"
	        })
	    }
	)
@Getter
@Setter
@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class TicketSequence extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private BankService service;

    @Column(name = "sequence_date", nullable = false)
    private LocalDate sequenceDate;

    @Column(nullable = false)
    private Integer lastNumber;

}
