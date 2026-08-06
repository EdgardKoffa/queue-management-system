package com.nsglobal.queue.audit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.dto.AuditResponseDto;
import com.nsglobal.queue.audit.entity.AuditLog;
import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.mapper.AuditMapper;
import com.nsglobal.queue.audit.repository.AuditRepository;
import com.nsglobal.queue.common.util.RemoteHttpDto;
import com.nsglobal.queue.common.util.UtilsService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditServiceImpl implements AuditService {
		
	private final AuditRepository auditRepository;

    private final AuditMapper auditMapper;
    
    private final UtilsService userService;
	/*
	 * Saving log
	 * */
	@Override
	public void log(AuditActionEnum action, ModulesNameEnum module, String description, boolean success) {
		String username=userService
				.getConnectedUserName();
		
		RemoteHttpDto remote=userService.getRemoteHostInfo();
		System.out.println("");
		
		auditRepository.save(
				AuditLog
				.builder()
				.action(action)
				.description(description)
				.success(success)
				.module(module)
				.httpMethod(remote.getHttpMethod())
				.userAgent(remote.getUserAgent())
				.ipAddress(remote.getIpAddress())
				.username(username)
				.build());
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public AuditResponseDto findById(Long id) {
		 AuditLog audit = auditRepository.findById(id)
		            .orElseThrow(() -> new EntityNotFoundException(
		                    "Journal d'audit introuvable."));

		    return auditMapper.toResponse(audit);

	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AuditResponseDto> findAll() {
		 return auditMapper.toResponses(
		            auditRepository.findAll());
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AuditResponseDto> findByUsername(String username) {
		return auditMapper.toResponses(
	            auditRepository
	                    .findByUsernameOrderByCreatedAtDesc(username));
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AuditResponseDto> findByAction(AuditActionEnum action) {
		return auditMapper.toResponses(
	            auditRepository
	                    .findByActionOrderByCreatedAtDesc(action));
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AuditResponseDto> findByModule(ModulesNameEnum module) {
		return auditMapper.toResponses(
	            auditRepository
	                    .findByModuleOrderByCreatedAtDesc(module));
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AuditResponseDto> findBetween(LocalDateTime start, LocalDateTime end) {
		return auditMapper.toResponses(
	            auditRepository
	                    .findByCreatedAtBetweenOrderByCreatedAtDesc(start, end));
	}

	@Override
	public Page<AuditResponseDto> findAll(Pageable pageable) {
		
		return auditRepository.findAll(pageable).map(auditMapper::toResponse);
	}

	@Override
	public void log(String username, AuditActionEnum action, ModulesNameEnum module, String description,
			boolean success) {			
			RemoteHttpDto remote=userService.getRemoteHostInfo();
			
			auditRepository.save(
					AuditLog
					.builder()
					.action(action)
					.description(description)
					.username(username)
					.success(success)
					.module(module)
					.httpMethod(remote.getHttpMethod())
					.userAgent(remote.getUserAgent())
					.ipAddress(remote.getIpAddress())
					.build());		
	}

}
