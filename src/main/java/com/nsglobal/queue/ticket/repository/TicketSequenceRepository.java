package com.nsglobal.queue.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.ticket.entity.TicketSequence;

public interface TicketSequenceRepository extends JpaRepository<TicketSequence, Long> {
	
	
}
