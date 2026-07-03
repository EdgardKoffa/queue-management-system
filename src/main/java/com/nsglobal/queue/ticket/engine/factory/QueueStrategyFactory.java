package com.nsglobal.queue.ticket.engine.factory;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.common.enums.QueueAlgorithm;
import com.nsglobal.queue.ticket.engine.strategy.FifoStrategy;
import com.nsglobal.queue.ticket.engine.strategy.Prioritystrategy;
import com.nsglobal.queue.ticket.engine.strategy.QueueSelectionStrategy;
import com.nsglobal.queue.ticket.engine.strategy.RoundRobinStrategy;
import com.nsglobal.queue.ticket.engine.strategy.VipStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueStrategyFactory {

	private final FifoStrategy fifoStrategy;

	private final Prioritystrategy priorityStrategy;

	private final VipStrategy vipStrategy;

	private final RoundRobinStrategy roundRobinStrategy;

	public QueueSelectionStrategy getStrategy(QueueAlgorithm algorithm) {

		return switch (algorithm) {

		case FIFO -> fifoStrategy;

		case PRIORITY -> priorityStrategy;

		case ROUNDROBIN -> roundRobinStrategy;

		case VIP -> vipStrategy;

		default -> throw new IllegalArgumentException("Valeur non espererée : " + algorithm);

		};

	}
}
