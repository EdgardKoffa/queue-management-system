package com.nsglobal.queue.ticket.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nsglobal.queue.ticket.dto.TicketRequestDto;
import com.nsglobal.queue.ticket.dto.TicketResponseDto;
import com.nsglobal.queue.ticket.entity.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {
	
	@Mapping(target = "branch",ignore = true)
	@Mapping(target = "service",ignore = true)
	@Mapping(target = "counter",ignore = true)
	public Ticket toEntity(TicketRequestDto dto);
	
	@Mapping(target = "branchId",source = "branch.id")
	@Mapping(target = "serviceId",source = "service.id")
	@Mapping(target = "counterId",source = "counter.id")
	
	@Mapping(target = "branchName",source = "branch.name")
	@Mapping(target = "serviceName",source = "service.name")
	@Mapping(target = "counterName",source = "counter.name")
	public TicketResponseDto toTicketResponsDto(Ticket ticket);
	
	
	@Mapping(target = "branchId",source = "branch.id")
	@Mapping(target = "serviceId",source = "service.id")
	@Mapping(target = "counterId",source = "counter.id")
	
	@Mapping(target = "branchName",source = "branch.name")
	@Mapping(target = "serviceName",source = "service.name")
	@Mapping(target = "counterName",source = "counter.name")
	public List<TicketResponseDto> toListTicketResponsDto(List<Ticket> tickets);
	
}
