package com.nsglobal.queue.bankservice.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.bankservice.mapper.BankServiceMapper;
import com.nsglobal.queue.bankservice.repository.BankServiceRepository;
import com.nsglobal.queue.bankservice.service.BankServiceService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.constant.ApiMessages;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceServiceImpl implements BankServiceService {

	private final BankServiceRepository bankserviceRepo;
	private final BankServiceMapper mapper;
	private final BranchRepository branchRepo;
	private final AuditService audit;
	
	private BankService getById(Long id) {
		BankService serv=bankserviceRepo.findById(id)
				.orElseThrow(null);
		
		return serv;
	}
	
	@Transactional
	@Override
	public ApiResponse<BankServiceResponseDto> create(BankServiceRequestDto bankService) {
		
		Branch branch = branchRepo.findById(bankService.getBranchId())
				.orElseThrow(null);
		if(branch==null) {
			String msg=ApiMessages.NOTFOUND_BRANCH;
			audit.log(AuditActionEnum.CREATE_SERVICE, 
					ModulesNameEnum.SERVICE, 
					"⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		
		BankService bk = mapper.toEntity(bankService);
		
		bk.setBranch(branch);
		BankServiceResponseDto saved=mapper.toBrancResponseDto(bankserviceRepo.save(bk));
	
		audit.log(AuditActionEnum.CREATE_SERVICE, 
				ModulesNameEnum.SERVICE, 
				"✅️"+ApiMessages.SUCCESS, true);
				
		return ResponseBuilder.success(ApiMessages.SUCCESS, saved);
	}
	
	@Transactional
	@Override
	public ApiResponse<BankServiceResponseDto> update(Long id, BankServiceRequestDto dto) {
	//	System.out.println("BankServiceRequestDto %s".formatted(dto.getDescription()));
		BankService existance = getById(id);
		if(existance==null) {
			String msg=ApiMessages.NOTFOUND;
			audit.log(AuditActionEnum.UPDATE_SERVICE, 
					ModulesNameEnum.SERVICE, 
					"⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		Branch branch = branchRepo.findById(dto.getBranchId())
				.orElseThrow(null);
		if(branch==null) {
			String msg=ApiMessages.NOTFOUND_BRANCH;
			audit.log(AuditActionEnum.UPDATE_SERVICE, 
					ModulesNameEnum.SERVICE, 
					"⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
			
		if(branch.getId()!=existance.getBranch().getId()) {
			existance.setBranch(branch);
		}
		existance.setCode(dto.getCode());
		existance.setActive(dto.getActive());
		existance.setPrefix(dto.getPrefix());
		existance.setName(dto.getName());
		existance.setPriority(dto.getPriority());
		existance.setDescription(dto.getDescription());
		existance.setEstimatedDurationMinutes(dto.getEstimatedDurationMinutes());
		// existance.
		//✅⚠
		BankServiceResponseDto updated=mapper.toBrancResponseDto(bankserviceRepo.save(existance));
		
		audit.log(AuditActionEnum.CREATE_SERVICE, 
				ModulesNameEnum.SERVICE, 
				"✅️"+ApiMessages.SUCCESS, true);
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, updated);
	}

	@Transactional(readOnly = true)
	@Override
	public ApiResponse<BankServiceResponseDto> findById(Long id) {

		return ResponseBuilder.success(ApiMessages.SUCCESS,mapper.toBrancResponseDto(getById(id)));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BankServiceResponseDto> findAll(Pageable page) {
		
		return bankserviceRepo.findAll(page)
				.map(mapper::toBrancResponseDto);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ApiResponse<List<BankServiceResponseDto>> findAll() {
		List<BankServiceResponseDto> list=mapper.toListBranchResponses(bankserviceRepo.findAll());
		
		return ResponseBuilder.success(ApiMessages.SUCCESS, list);
	}
	
	@Transactional
	@Override
	public ApiResponse<BankServiceResponseDto> delete(Long id) {
		BankService existance = getById(id);
		if(existance==null) {
			String msg=ApiMessages.NOTFOUND;
			audit.log(AuditActionEnum.DELETE_SERVICE, 
					ModulesNameEnum.SERVICE, 
					"⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		existance.setDeletedAt(LocalDateTime.now());
		BankServiceResponseDto deleted=mapper.toBrancResponseDto(bankserviceRepo.save(existance));
		audit.log(AuditActionEnum.DELETE_SERVICE, 
				ModulesNameEnum.SERVICE, 
				"✅️"+ApiMessages.SUCCESS, true);
		return ResponseBuilder.success(ApiMessages.SUCCESS, deleted);
	}

	@Override
	public ApiResponse<BankServiceResponseDto> changeActivateState(Long id, boolean state) {
		BankService existance = getById(id);
		if(existance==null) {
			String msg=ApiMessages.NOTFOUND;
			audit.log(AuditActionEnum.CHANGE_IS_ACTIVATE, 
					ModulesNameEnum.SERVICE, 
					"⚠️"+msg, false);
			return ResponseBuilder.error(msg);
		}
		existance.setActive(state);
		BankServiceResponseDto pacthed=mapper.toBrancResponseDto(bankserviceRepo.save(existance));
		audit.log(AuditActionEnum.CHANGE_IS_ACTIVATE, 
				ModulesNameEnum.SERVICE, 
				"✅️"+ApiMessages.SUCCESS, true);
		return ResponseBuilder.success(ApiMessages.SUCCESS, pacthed);
	}

}
