package com.nsglobal.queue.audit.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.audit.dto.AuditResponseDto;
import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.common.constant.ApiRoutes;

import lombok.RequiredArgsConstructor;


@RequestMapping(ApiRoutes.API_V1+"/audit")
@RestController
@RequiredArgsConstructor
public class AuditController {
	private final AuditService auditService;
	
	
	@GetMapping
	public Page<AuditResponseDto> findAll(Pageable pageable){
		
		return auditService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
    public AuditResponseDto findById(
            @PathVariable Long id) {

        return auditService.findById(id);

    }

    @GetMapping("/user/{username}")
    public List<AuditResponseDto> findByUsername(
            @PathVariable String username) {

        return auditService.findByUsername(username);

    }

    @GetMapping("/action/{action}")
    public List<AuditResponseDto> findByAction(
            @PathVariable AuditActionEnum action) {

        return auditService.findByAction(action);

    }

    @GetMapping("/module/{module}")
    public List<AuditResponseDto> findByModule(
            @PathVariable ModulesNameEnum module) {

        return auditService.findByModule(module);

    }

    @GetMapping("/search")
    public List<AuditResponseDto> findBetween(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return auditService.findBetween(start, end);

    }

}
