package com.nsglobal.queue.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.ticket.entity.TicketHistory;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {

}
