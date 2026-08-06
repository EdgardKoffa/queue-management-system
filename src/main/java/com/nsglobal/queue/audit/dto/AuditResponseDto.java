package com.nsglobal.queue.audit.dto;

import java.time.LocalDateTime;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponseDto {
	
	 private Long id;

	    private String username;

	    private AuditActionEnum action;

	    private ModulesNameEnum module;

	    private String description;

	    private String ipAddress;

	    private boolean success;

	    private LocalDateTime createdAt;
	    
	    private String sessionId;

	    private String requestUri;

	    private String httpMethod;

	    private String userAgent;
}
