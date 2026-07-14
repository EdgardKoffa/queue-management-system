package com.nsglobal.queue.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.nsglobal.queue.appointment.enums.AppointmentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AppointmentResponseDto {
	 private Long id;

	    private String customerName;

	    private String phoneNumber;

	    private String email;

	    private Long branchId;

	    private String branchName;

	    private Long serviceId;

	    private String serviceName;

	    private LocalDate appointmentDate;

	    private LocalTime appointmentTime;

	    private AppointmentStatus status;

	    private String qrCode;

	    private Long ticketId;

	    private LocalDateTime createdAt;

}
