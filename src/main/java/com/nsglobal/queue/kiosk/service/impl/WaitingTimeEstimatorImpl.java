package com.nsglobal.queue.kiosk.service.impl;

import org.springframework.stereotype.Service;

import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.kiosk.service.WaitingTimeEstimator;
import com.nsglobal.queue.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaitingTimeEstimatorImpl implements WaitingTimeEstimator {

	  private final TicketRepository ticketRepository;

	    private final CounterRepository counterRepository;

	    @Override
	    public Integer estimate(Long branchId,
	                            Long serviceId) {

	        long waitingTickets =
	                ticketRepository.countByBranchAndServiceAndStatus(
	                        branchId,
	                        serviceId,
	                        TicketStatus.WAITING
	                        );

	        long openCounters =
	                counterRepository.countOpenByBranch(branchId);

	        if (openCounters == 0) {
	            return 0;
	        }

	        int averageServiceTime = 5;

	        return (int) Math.ceil(
	                (double) waitingTickets * averageServiceTime
	                        / openCounters);

	    }



}
