package com.nsglobal.queue.ticket.engine.strategy;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.ticket.entity.Ticket;
import com.nsglobal.queue.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Prioritystrategy implements QueueSelectionStrategy {

	private final TicketRepository repository;

	@Override
	public Ticket selectNextTicket(Counter counter) {

		return repository.findFirstByBranchAndStatusOrderByPriorityDescIssueTimeAsc(counter.getBranch(),TicketStatus.WAITING).orElseThrow();
	}

}
