package com.nsglobal.queue.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
public enum SettingKeysEnum {
	VOCAL_MESSAGE("Ticket %s veuillez vous presenter au guichet %s"),
	ENTERPRISE_NAME("NS GLOBAL BANK"),
	COUNTER_OPEN_CRON("0 0 8 * * MON-FRI"),
	COUNTER_CLOSE_CRON("0 0 18 * * MON-FRI"),
	DASHBOARD_RFRESH_CRON("*/30 * * * * *"),
	ABSENT_TICKET_CRON("0 * * * * *"),
	MINIMUN_WAITTING_TIME("5"),
	APPOINTMENT_MAX("5"),
	APPOINTMENT_START("8"),
	APPOINTMENT_END("18");
	
	@Setter
	@Getter
	private String defaultValue;
}
