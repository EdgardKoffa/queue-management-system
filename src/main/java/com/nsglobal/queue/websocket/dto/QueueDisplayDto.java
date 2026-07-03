package com.nsglobal.queue.websocket.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueDisplayDto {
	private Long ticketId;

	private String ticketNumber;

	private String service;

	private String counter;

	private String branch;

	private LocalDateTime callTime;

}
