package com.nsglobal.queue.ticket.engine.strategy;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.ticket.entity.Ticket;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoundRobinStrategy implements QueueSelectionStrategy {

	@Override
	public Ticket selectNextTicket(Counter counter) {

		return null;
	}

}
