package com.nsglobal.queue.display.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Pour un affichage du ticket.
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayTicketDto {

	private Long tickedId;

	private String ticketNumber;

	private String service;

	private String counter;

	private String operator;

	private LocalDateTime callTime;
}
