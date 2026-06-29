package com.nsglobal.queue.agency.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.entity.Agency;

@Mapper(componentModel = "spring")
public interface AgencyMapper {

    Agency toEntity(AgencyRequestDto dto);

    AgencyResponseDto toResponseDto(Agency agency);
    
    List<AgencyResponseDto> toListResponseDto(List<Agency> agencies);

}
