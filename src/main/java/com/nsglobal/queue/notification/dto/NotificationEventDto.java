package com.nsglobal.queue.notification.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationEventDto {
	
	private boolean success;
	
	private String type;

    private String channel;

    private String recipient;

    private String message;
    
    private LocalDateTime dateTime;
    
}
