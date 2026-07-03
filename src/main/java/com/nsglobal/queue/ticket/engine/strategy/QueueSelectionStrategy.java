package com.nsglobal.queue.ticket.engine.strategy;

import com.nsglobal.queue.counter.entity.Counter;
import com.nsglobal.queue.ticket.entity.Ticket;

public interface QueueSelectionStrategy {
	public Ticket selectNextTicket(Counter counter);

}
