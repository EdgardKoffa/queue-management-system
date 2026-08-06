package com.nsglobal.queue.agency.service.impl;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.agency.dto.AgencyRequestDto;
import com.nsglobal.queue.agency.dto.AgencyResponseDto;
import com.nsglobal.queue.agency.entity.Agency;
import com.nsglobal.queue.agency.mapper.AgencyMapper;
import com.nsglobal.queue.agency.repository.AgencyRepository;
import com.nsglobal.queue.agency.service.AgencyService;
import com.nsglobal.queue.audit.enums.AuditActionEnum;
import com.nsglobal.queue.audit.enums.ModulesNameEnum;
import com.nsglobal.queue.audit.service.AuditService;
import com.nsglobal.queue.common.constant.ApiMessages;
import com.nsglobal.queue.common.enums.EnumStatus;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
import com.nsglobal.queue.common.response.ApiResponse.ApiResponseBuilder;
import com.nsglobal.queue.common.util.Utilities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgencyServiceImpl implements AgencyService {

	private final AgencyRepository agencyRepository;

	private final AgencyMapper mapper;
	
	private final AuditService audit;

	
	
	
	@Transactional(readOnly = true)
	private Agency getById(Long id) {
		Agency agc=agencyRepository.findById(id).orElse(null
				//()->new RuntimeException("Aucune agence trouvée.")
				);
		
			return agc;
	}
	
	@Transactional
	@Override
	public ApiResponse<AgencyResponseDto> create(AgencyRequestDto agency) {
		
		ApiResponseBuilder <AgencyResponseDto> apiResponse=ApiResponse.<AgencyResponseDto>builder();
		
		Agency agc=agencyRepository.save(mapper.toEntity(agency));
		String msg=(Utilities.isLangFr()?"Agence %s créée.":"Agency %s created.").formatted(agency.getName());
		ApiResponse<AgencyResponseDto> respone=	apiResponse
				.data(mapper.toResponseDto(agc))
				.date(LocalDateTime.now())
				.success(true)
				.message(msg).build();
		
		audit.log(AuditActionEnum.CREATE, ModulesNameEnum.AGENCY, "✅ "+msg, true);
		
		return respone;
	}

	@Transactional(readOnly = true)
	@Override
	public ApiResponse<AgencyResponseDto> findById(Long id) {
		ApiResponseBuilder <AgencyResponseDto> apiResponse=ApiResponse.<AgencyResponseDto>builder();
		Agency agc=getById(id);
		if(agc!=null&&agc.getStatus()!=EnumStatus.ACTIVE) {
			String msg="Cette agence n'est pas active.";
			ApiResponse<AgencyResponseDto> respone=	apiResponse
					.data(null)
					.date(LocalDateTime.now())
					.success(false)
					.message(msg).build();
					//audit.log(AuditActionEnum.CHANGE_STATUS, ModulesNameEnum.AGENCY, "❌ "+msg, false);
					return respone;
		}
		String msg=ApiMessages.DETAILED;
		ApiResponse<AgencyResponseDto> respone=	apiResponse
				.data(mapper.toResponseDto(agc))
				.date(LocalDateTime.now())
				.success(true)
				.message(msg).build();
			//	audit.log(AuditActionEnum.CHANGE_STATUS, ModulesNameEnum.AGENCY, "❌ "+msg, false);
				return respone;
	
	}

	@Transactional(readOnly = true)
	@Override
	public Page<AgencyResponseDto> findAll(Pageable pageable) {
		//System.out.println("---------> "+pageable.getPageSize());
		Page<AgencyResponseDto> pageResponse=agencyRepository
				.findAll(pageable)
				.map(mapper::toResponseDto);
		
		return pageResponse;
	}

	@Override
	public void delete(Long id) {
		agencyRepository.deleteById(id);
	}
	
	
	@Transactional
	@Override
	public ApiResponse<AgencyResponseDto> update(Long id, AgencyRequestDto dto) {
		ApiResponseBuilder <AgencyResponseDto> apiResponse=ApiResponse.<AgencyResponseDto>builder();
		Agency existingAgency = getById(id);// findById(id);
		if(existingAgency==null) {
			String msg=ApiMessages.NOTFOUND;
			ApiResponse<AgencyResponseDto> respone=	apiResponse
					.data(null)
					.date(LocalDateTime.now())
					.success(false)
					.message(msg).build();
					audit.log(AuditActionEnum.UPDATE, ModulesNameEnum.AGENCY, "❌ "+msg, false);
					return respone;
		}
		if(existingAgency.getStatus()!=EnumStatus.ACTIVE) {
			String msg=ApiMessages.NOTFOUND;
			ApiResponse<AgencyResponseDto> respone=	apiResponse
					.data(null)
					.date(LocalDateTime.now())
					.success(false)
					.message(msg).build();
					audit.log(AuditActionEnum.UPDATE, ModulesNameEnum.AGENCY, "❌ "+msg, false);
					return respone;
		}
		Agency agency = mapper.toEntity(dto);
		existingAgency.setCode(agency.getCode());
		existingAgency.setName(agency.getName());
		existingAgency.setPhone(agency.getPhone());
		existingAgency.setEmail(agency.getEmail());
		existingAgency.setStatus(agency.getStatus());
		
		String msg=ApiMessages.UPDATED;
		ApiResponse<AgencyResponseDto> respone=	apiResponse
				.data(mapper.toResponseDto(agencyRepository.save(existingAgency)))
				.date(LocalDateTime.now())
				.success(true)
				.message(msg).build();
				audit.log(AuditActionEnum.UPDATE, ModulesNameEnum.AGENCY, "✅ "+msg, true);
				return respone;
		
	}
	
	@Transactional
	@Override
	public ApiResponse<AgencyResponseDto> changeStatus(EnumStatus status, Long id) {
		ApiResponseBuilder <AgencyResponseDto> apiResponse=ApiResponse.<AgencyResponseDto>builder();
		int result=agencyRepository.updateStatusById(status, id);
		System.out.println("result =====>>> "+result);
		Agency updated=agencyRepository.findById(id).orElse(null);
		if(result==0||updated==null) {
			String msg="Le changement de statut en '%s' de la compagie a échoué.".formatted(status);
			ApiResponse<AgencyResponseDto> respone=	apiResponse
					.data(null)
			.date(LocalDateTime.now())
			.success(false)
			.message(msg).build();
			audit.log(AuditActionEnum.CHANGE_STATUS, ModulesNameEnum.AGENCY, "❌ "+msg, false);
			return respone;
		}
		
		String msg="Le changement de statut en '%s' de la compagie a été éffectué."
				.formatted(status);
		ApiResponse<AgencyResponseDto> respone=	apiResponse.data(mapper.toResponseDto(updated))
				.date(LocalDateTime.now())
				.success(true)
				.message(msg).build();
		
		audit.log(AuditActionEnum.CHANGE_STATUS, ModulesNameEnum.AGENCY,
				"✅ "+msg, true);
		//
		return respone;
	}

	@Transactional
	@Override
	public ApiResponse<List<AgencyResponseDto>> findAll() {
		List<Agency> agcList=agencyRepository.findAll();
		return ResponseBuilder.success("Liste recupere", 
				agcList.stream()
				.filter(p->p.getDeletedAt()==null)
				.map(mapper::toResponseDto).toList());
	}

}
