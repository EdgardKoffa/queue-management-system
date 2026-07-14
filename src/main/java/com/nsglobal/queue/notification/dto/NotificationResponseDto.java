package com.nsglobal.queue.notification.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationResponseDto {

    private boolean success;

    private String channel;

    private String recipient;

    private String message;

}