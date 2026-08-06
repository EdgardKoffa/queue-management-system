package com.nsglobal.queue.audit.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.nsglobal.queue.audit.dto.AuditResponseDto;
import com.nsglobal.queue.audit.entity.AuditLog;

@Mapper(componentModel = "spring")
public interface AuditMapper {
	
	
	AuditResponseDto toResponse(AuditLog audit);

    List<AuditResponseDto> toResponses(List<AuditLog> audits);
}
