package com.nsglobal.queue.ticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	// Strategy PRIORITY
	Optional<Ticket> findFirstByBranchAndStatusOrderByPriorityDescIssueTimeAsc(
	        Branch branch,
	        TicketStatus status
	);
	
	//strategy Fifo
	Optional<Ticket> findFirstByBranchAndStatusOrderByIssueTimeAsc(Branch branch, TicketStatus status);
	

}
