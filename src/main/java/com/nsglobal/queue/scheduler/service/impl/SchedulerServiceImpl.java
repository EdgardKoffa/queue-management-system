package com.nsglobal.queue.scheduler.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.counter.service.CounterService;
import com.nsglobal.queue.dashboard.service.DashboardService;
import com.nsglobal.queue.scheduler.config.SchedulerProperties;
import com.nsglobal.queue.scheduler.service.SchedulerService;
import com.nsglobal.queue.ticket.service.TicketService;
import com.nsglobal.queue.websocket.service.QueueNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerServiceImpl implements SchedulerService {
	
	//private final Ticket ticketSequenceService;
	private final SchedulerProperties props;
    private final CounterService counterService;

    private final TicketService ticketService;

    private final QueueNotificationService notificationService;

	

	@Override
	public void openCounters() {
		
		counterService.openAllCounters();
	}

	@Override
	public void closeCounters() {
		counterService.closeAllCounters();
		
	}

	@Override
	public void refreshDashboard() {
		
		notificationService.publishDashboard();
	}

	@Override
	public void processAbsentTickets() {
		
		ticketService.proccessingAbsentTicket(props.getAbsentDelayMinutes().longValue());
	}

	@Override
	public void cleanup() {
		
		
	}

}
