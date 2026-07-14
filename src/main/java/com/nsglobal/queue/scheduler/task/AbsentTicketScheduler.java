package com.nsglobal.queue.scheduler.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nsglobal.queue.scheduler.service.SchedulerService;
import com.nsglobal.queue.settings.SettingKeys;
import com.nsglobal.queue.settings.SettingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor

public class AbsentTicketScheduler {
	
	private final SchedulerService schedulerService;
	private final SettingService service;
	
	
//	@Scheduled(cron = "${com.nsglobal.queue.absentTicketCron}")
	public void processAbsentTickets() {
		try {
			log.info("Checking absent tickets..."+service.getValue(SettingKeys.ABSENT_TICKET_CRON));
			schedulerService.processAbsentTickets();
		} catch (Exception e) {
			
			log.warn("Erreur proccessing absent ticket",e);
		}
	}
}
