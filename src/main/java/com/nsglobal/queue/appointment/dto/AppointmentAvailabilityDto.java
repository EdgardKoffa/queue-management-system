package com.nsglobal.queue.appointment.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentAvailabilityDto {
	
	 private LocalTime appointmentTime;

	    private Integer availableSlots;

	    private Boolean available;

}
