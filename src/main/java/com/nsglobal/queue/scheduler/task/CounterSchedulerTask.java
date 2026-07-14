package com.nsglobal.queue.scheduler.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nsglobal.queue.scheduler.service.SchedulerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CounterSchedulerTask {
	
	private final SchedulerService schedulerService;
	
	 @Scheduled(cron = "${com.nsglobal.queue.counterOpenCron}")
	 public void openActiveCounters() {
		 try {
			 log.info("===== AUTOMATIC COUNTER OPENING =====");
			 schedulerService.openCounters();
		} catch (Exception e) {
			log.warn("Erreur d'ouverture automatique des guichet",e.getMessage());
		}
	 }
	 
	 @Scheduled(cron ="${com.nsglobal.queue.counterCloseCron}")
	 public void closeActiveCounters() {
		 try {
			 log.info("===== AUTOMATIC COUNTER CLOSING =====");
			 schedulerService.closeCounters();
		} catch (Exception e) {
			log.warn("Erreur de fermeture automatique des guichet",e.getMessage());
		}
	 }

}
