package com.nsglobal.queue.branch.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.entity.Agency;
import com.nsglobal.queue.agency.repository.AgencyRepository;
import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.mapper.BranchMapper;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.branch.service.BranchService;
import com.nsglobal.queue.common.constant.ApiMessages;
import com.nsglobal.queue.common.enums.EnumStatus;
import com.nsglobal.queue.common.exception.GlobalExceptionHandler;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
import com.nsglobal.queue.common.response.ApiResponse.ApiResponseBuilder;
import com.nsglobal.queue.user.entity.User;
import com.nsglobal.queue.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

	private final BranchRepository brancRepo;
	private final BranchMapper mapper;
	private final AgencyRepository agencyRepo;
	private final HttpServletRequest http;
	private final UserRepository userRepo;
	private final AuditService audit;
	
	@Transactional(readOnly = true)
	private  Branch getById(Long id) {
		
		Branch branch = brancRepo.findById(id)
				.orElse(null);
		
		if(branch==null||branch.getStatus()!=EnumStatus.ACTIVE) {
			return null;
		}
		return branch;
	}
	@Transactional
	@Override
	public ApiResponse<BranchResponseDto> create(BranchRequestDto branch) {
		ApiResponseBuilder <BranchResponseDto> apiResponse=ApiResponse.<BranchResponseDto>builder();
		Agency agency = agencyRepo.findById(branch.getAgencyId())
				.orElse(null);
		
		if(agency==null) {
			String msg=ApiMessages.AGENCY_NOT_FOUND;
			ApiResponse<BranchResponseDto> rsp=apiResponse.data(null)
					.message(msg)
					.success(false)
					.build();
			audit.log(AuditActionEnum.CREATE_BRANCH, 
					ModulesNameEnum.BRANCH, 
					msg, false);
			return rsp;
		}
		Branch b = mapper.toEntity(branch);
		b.setAgency(agency);
		User user=userRepo.findByUserName(http.getRemoteUser()).orElse(null);
		if(user==null) {
			String msg=ApiMessages.USER_NOT_FOUND;
			ApiResponse<BranchResponseDto> rsp=apiResponse.data(null)
					.message(msg)
					.success(false)
					.build();
			audit.log(AuditActionEnum.CREATE_BRANCH, 
					ModulesNameEnum.BRANCH, 
					msg, false);
			return rsp;
		}
		Branch newBranch=brancRepo.save(b);
		
		user.setBranch(newBranch);
		userRepo.save(user);
		ApiResponse<BranchResponseDto> rsp=apiResponse
				.data(mapper.toBrancResponseDto(newBranch))
				.message(ApiMessages.CREATED)
				.success(true)
				.build();
		audit.log(user.getUserName(),AuditActionEnum.CREATE_BRANCH, 
				ModulesNameEnum.BRANCH, 
				ApiMessages.CREATED, true);
		return rsp;
	}
	
	
	@Transactional
	@Override
	public ApiResponse<BranchResponseDto> update(Long id, BranchRequestDto dto) {
		ApiResponseBuilder <BranchResponseDto> apiResponse=ApiResponse.<BranchResponseDto>builder();
		Branch existanceBranch = getById(id);
		if(existanceBranch==null) {
			String msg=ApiMessages.NOTFOUND;
			ApiResponse<BranchResponseDto> rsp=apiResponse.data(null)
					.message(msg)
					.success(false)
					.build();
			audit.log(AuditActionEnum.UPDATE_BRANCH, 
					ModulesNameEnum.BRANCH, 
					msg, false);
			return rsp;
		}
		Branch branch = mapper.toEntity(dto);

		existanceBranch.setCity(branch.getCity());
		existanceBranch.setAddress(branch.getAddress());
		existanceBranch.setAgency(branch.getAgency());
		existanceBranch.setEmail(branch.getEmail());
		existanceBranch.setPhone(branch.getPhone());
		existanceBranch.setName(branch.getName());
		existanceBranch.setStatus(branch.getStatus());
		existanceBranch.setCode(branch.getCode());
		ApiResponse<BranchResponseDto> rsp=apiResponse
				.data(
						mapper.toBrancResponseDto(brancRepo.save(existanceBranch))
						)
				.message(ApiMessages.UPDATED)
				.success(true)
				.build();
		audit.log(AuditActionEnum.UPDATE_BRANCH, 
				ModulesNameEnum.BRANCH, 
				ApiMessages.UPDATED
				, true);
		
		return rsp;
	}

	@Override
	public ApiResponse<BranchResponseDto>  findById(Long id) {
		ApiResponseBuilder <BranchResponseDto> apiResponse=ApiResponse.<BranchResponseDto>builder();
		Branch existanceBranch = getById(id);
		if(existanceBranch==null) {
			String msg=ApiMessages.NOTFOUND;
			ApiResponse<BranchResponseDto> rsp=apiResponse.data(null)
					.message(msg)
					.success(false)
					.build();
			audit.log(AuditActionEnum.UPDATE_BRANCH, 
					ModulesNameEnum.BRANCH, 
					msg, false);
			return rsp;
		}
		ApiResponse<BranchResponseDto> rsp=apiResponse
				.data(mapper.toBrancResponseDto( getById(id))
						)
				.message(ApiMessages.UPDATED)
				.success(true)
				.build();
		return rsp;
	}

	
	@Override
	public Page<BranchResponseDto> findAll(Pageable page) {

		return brancRepo.findAll(page)
				.map(mapper::toBrancResponseDto);
	}

	@Override
	public ApiResponse<BranchResponseDto> delete(Long id) {
		Branch b=getById(id);
		if(b==null) {
			String msg=ApiMessages.NOTFOUND;
			audit.log(AuditActionEnum.DELETE_BRANCH, 
					ModulesNameEnum.BRANCH,msg, false);
			return ResponseBuilder.error(msg);
		}
		b.setDeletedAt(LocalDateTime.now());
		Branch deleted= brancRepo.save(b);
		String msg=ApiMessages.DELETED;
		audit.log(AuditActionEnum.UPDATE_BRANCH, 
				ModulesNameEnum.BRANCH,msg, true);
		return ResponseBuilder.success(msg, mapper.toBrancResponseDto(deleted));
	}
	
	@Override
	public ApiResponse<BranchResponseDto> changeStatus(EnumStatus status, Long id) {
		ApiResponseBuilder <BranchResponseDto> apiResponse=ApiResponse.<BranchResponseDto>builder();
		Branch updated=brancRepo.updateStatusById(status, id).orElse(null);
		if(updated==null) {
			String msg="Le changement de statut en '%s' de la succursale a échoué.".formatted(status);
			ApiResponse<BranchResponseDto> respone=	apiResponse.data(null)
			.date(LocalDateTime.now())
			.success(false)
			.message(msg).build();
			audit.log(AuditActionEnum.CHANGE_STATUS, ModulesNameEnum.BRANCH, "❌ "+msg, false);
			return respone;
		}
		String msg="Le changement de statut en '%s' de la succursale a été éffectué."
				.formatted(status);
		ApiResponse<BranchResponseDto> respone=	apiResponse.data(mapper.toBrancResponseDto(updated))
				.date(LocalDateTime.now())
				.success(true)
				.message(msg).build();
		
		audit.log(AuditActionEnum.CHANGE_STATUS, ModulesNameEnum.BRANCH,
				"✅ "+msg, true);
		//
		return respone;
	}

	@Override
	public ApiResponse<List<BranchResponseDto>> findAll() {
		ApiResponseBuilder <List<BranchResponseDto>> apiResponse=ApiResponse.<List<BranchResponseDto>>builder();
		ApiResponse<List<BranchResponseDto>> rsp=apiResponse
				.data( brancRepo.findAll().stream()
						.filter(p->p.getDeletedAt()==null)
						.map(mapper::toBrancResponseDto).toList()
						)
				.message(ApiMessages.UPDATED)
				.success(true)
				.build();
		return rsp;
	}

}
