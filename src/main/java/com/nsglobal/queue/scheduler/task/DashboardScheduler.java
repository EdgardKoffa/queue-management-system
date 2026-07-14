package com.nsglobal.queue.scheduler.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nsglobal.queue.scheduler.service.SchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardScheduler {
	
	private final SchedulerService schedulerService;
	
	//@Scheduled(cron = "${com.nsglobal.queue.dashboardRefreshCron}")
	public void refreshDashboard() {
		
		try {
			schedulerService.refreshDashboard();
		} catch (Exception e) {
			log.warn("Erreur cron refresing dashboard",e.getMessage());
		}
	}
}
