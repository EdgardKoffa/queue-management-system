package com.nsglobal.queue.appointment.service;

import java.time.LocalDate;
import java.util.List;

import com.nsglobal.queue.appointment.dto.AppointmentAvailabilityDto;
import com.nsglobal.queue.appointment.dto.AppointmentRequestDto;
import com.nsglobal.queue.appointment.dto.AppointmentResponseDto;
import com.nsglobal.queue.common.util.ApiResponseDto;

public interface AppointmentService {
	
	 AppointmentResponseDto create(AppointmentRequestDto request);

	    AppointmentResponseDto findById(Long id);

	    List<AppointmentResponseDto> findAll();

	    AppointmentResponseDto update(Long id,
	                                  AppointmentRequestDto request);

	    ApiResponseDto cancel(Long id);

	    AppointmentResponseDto checkIn(String qrCode);

	    List<AppointmentAvailabilityDto> getAvailableSlots(
	            Long branchId,
	            Long serviceId,
	            LocalDate date);

}
