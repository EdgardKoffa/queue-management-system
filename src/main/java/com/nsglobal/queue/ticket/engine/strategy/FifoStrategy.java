package com.nsglobal.queue.ticket.engine.strategy;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

/*
 * FIFO => Fisrt In First Out
 * **/

@Service
@RequiredArgsConstructor
public class FifoStrategy implements QueueSelectionStrategy {

	private final TicketRepository repository;

	@Override
	public Ticket selectNextTicket(Counter counter) {

		return repository
				.findFirstByBranchAndStatusOrderByIssueTimeAsc(counter.getBranch(), TicketStatus.WAITING)
				.orElseThrow();
	}

}
