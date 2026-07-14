package com.nsglobal.queue.counter.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nsglobal.queue.counter.dto.CounterRequestDto;
import com.nsglobal.queue.counter.dto.CounterResponseDto;
import com.nsglobal.queue.counter.entity.Counter;

@Mapper(componentModel = "spring")
public interface CounterMapper {

	@Mapping(target = "branch", ignore = true)
	@Mapping(target = "operator", ignore = true)
	public Counter toEntity(CounterRequestDto dto);

	@Mapping(target = "branchId", source = "branch.id")
	@Mapping(target = "branchName", source = "branch.name")
	@Mapping(target = "operatorId", source = "operator.id")
	@Mapping(target = "operatorUserName", source = "operator.userName")
	public CounterResponseDto toCounterResponse(Counter counter);

	@Mapping(target = "branchId", source = "branch.id")
	@Mapping(target = "branchName", source = "branch.name")//
	@Mapping(target = "operatorId", source = "operator.id")
	@Mapping(target = "operatorUserName", source = "operator.userName")
	public List<CounterResponseDto> toListCounterResponseDto(List<Counter> list);

}
