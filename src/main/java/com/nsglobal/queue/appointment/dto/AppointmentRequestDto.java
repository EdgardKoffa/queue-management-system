package com.nsglobal.queue.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequestDto {
	@NotBlank
    private String customerName;

    @NotBlank
    private String phoneNumber;

    @Email
    private String email;

    @NotNull
    private Long branchId;

    @NotNull
    private Long serviceId;

    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

}
