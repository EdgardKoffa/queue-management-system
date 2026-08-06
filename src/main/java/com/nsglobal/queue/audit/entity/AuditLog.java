package com.nsglobal.queue.audit.entity;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@Table(name = "audit_log")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog extends BaseEntity {
	
	 @Column(nullable = false, length = 50)
	    private String username;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, length = 50)
	    private AuditActionEnum action;
	    
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false, length = 50)
	  //  @Enumerated(EnumType.STRING) // très important
	   // @Column(name = "module", nullable = false)
	    private ModulesNameEnum module;

	    @Column(length = 500)
	    private String description;

	    @Column(length = 45)
	    private String ipAddress;
	    
	    @Column(length = 245)
	    private String userAgent;
	    
	    @Column(length = 50)
	    private String httpMethod;
	    
	    

	    @Column(nullable = false)
	    private boolean success;
}
