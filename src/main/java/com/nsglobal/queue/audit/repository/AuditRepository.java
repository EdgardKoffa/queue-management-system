package com.nsglobal.queue.audit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.audit.entity.AuditLog;
import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {
	 List<AuditLog> findByUsernameOrderByCreatedAtDesc(String username);

	    List<AuditLog> findByActionOrderByCreatedAtDesc(AuditActionEnum action);

	    List<AuditLog> findByModuleOrderByCreatedAtDesc(ModulesNameEnum module);

	    List<AuditLog> findBySuccess(boolean success);

	    List<AuditLog> findByCreatedAtBetweenOrderByCreatedAtDesc(
	            LocalDateTime start,
	            LocalDateTime end);
	    
	    Page<AuditLog> findAll(Pageable pageable);
}
