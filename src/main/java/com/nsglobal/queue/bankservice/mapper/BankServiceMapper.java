package com.nsglobal.queue.bankservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nsglobal.queue.bankservice.dto.BankServiceRequestDto;
import com.nsglobal.queue.bankservice.dto.BankServiceResponseDto;
import com.nsglobal.queue.bankservice.entity.BankService;


@Mapper(componentModel = "spring")
public interface BankServiceMapper {

  @Mapping(target = "branch", ignore = true)
	public BankService toEntity(BankServiceRequestDto bankService);
	
	@Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "branchName", source = "branch.name")
	public BankServiceResponseDto toBrancResponseDto(BankService bankService);
	
	@Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "branchName", source = "branch.name")
	public List<BankServiceResponseDto> toListBranchResponses(List<BankService> bankService);
 
}


