package com.nsglobal.queue.display.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Pour un message vocal sur ecrant TV LED.
 * */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
	private String language;
	private String message;
	private String tikcetNumber;
	private String Counter;
}
