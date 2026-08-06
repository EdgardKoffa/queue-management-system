package com.nsglobal.queue.audit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nsglobal.queue.audit.dto.AuditResponseDto;
import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;

public interface AuditService {
	
	void log(
            AuditActionEnum action,
            ModulesNameEnum module,
            String description,
            boolean success);
	
	void log(String username,
            AuditActionEnum action,
            ModulesNameEnum module,
            String description,
            boolean success);

    AuditResponseDto findById(Long id);

    List<AuditResponseDto> findAll();

    List<AuditResponseDto> findByUsername(String username);

    List<AuditResponseDto> findByAction(AuditActionEnum action);

    List<AuditResponseDto> findByModule(ModulesNameEnum module);
    
    List<AuditResponseDto> findBetween(
            LocalDateTime start,
            LocalDateTime end);
    
    Page<AuditResponseDto> findAll(Pageable pageable);

}
