package com.nsglobal.queue.settings;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(ApiRoutes.API_V1+"/settings")
@RequiredArgsConstructor
public class SettingController {
	
	private final SettingService service;

	@GetMapping("/{value}/"+SettingKeys.ABSENT_TICKET_CRON)
	public ResponseEntity<String> putabsent_ticket_cron(@PathVariable String value) {
		
		return ResponseEntity.ok(service.setValue(SettingKeys.ABSENT_TICKET_CRON, value));
	}
	
	@GetMapping("/{value}/"+SettingKeys.ENTERPRISE_NAME)
	public ResponseEntity<String> putenterprisename(@PathVariable String value) {
		
		return ResponseEntity.ok(service.setValue(SettingKeys.ENTERPRISE_NAME, value));
	}
	
	@GetMapping("/{value}/"+SettingKeys.COUNTER_OPEN_CRON)
	public ResponseEntity<String> putcounter_open(@PathVariable String value) {
		
		return ResponseEntity.ok(service.setValue(SettingKeys.COUNTER_OPEN_CRON, value));
	}
	
	
}
