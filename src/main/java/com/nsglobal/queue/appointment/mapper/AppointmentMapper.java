package com.nsglobal.queue.appointment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nsglobal.queue.appointment.dto.AppointmentResponseDto;
import com.nsglobal.queue.appointment.entity.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
	 @Mapping(target = "branchId", source = "branch.id")
	    @Mapping(target = "branchName", source = "branch.name")
	    @Mapping(target = "serviceId", source = "service.id")
	    @Mapping(target = "serviceName", source = "service.name")
	    @Mapping(target = "ticketId", source = "ticket.id")
	    AppointmentResponseDto toResponse(Appointment appointment);

	    List<AppointmentResponseDto> toResponses(List<Appointment> appointments);

}
