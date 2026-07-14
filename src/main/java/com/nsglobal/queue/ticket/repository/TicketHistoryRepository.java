package com.nsglobal.queue.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.ticket.entity.TicketHistory;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {
	
	@EntityGraph(attributePaths = {"ticket"})
	List<TicketHistory> findAll();
	
	@EntityGraph(attributePaths = {"ticket"})
	Optional<TicketHistory> findById(Long id);
	
	
}
