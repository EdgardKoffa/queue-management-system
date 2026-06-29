package com.nsglobal.queue.branch.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nsglobal.queue.branch.dto.BranchRequestDto;
import com.nsglobal.queue.branch.dto.BranchResponseDto;
import com.nsglobal.queue.branch.entity.Branch;

@Mapper(componentModel = "spring")
public interface BranchMapper {
	
	@Mapping(target = "agency", ignore = true)
	public Branch toEntity(BranchRequestDto branch);
	
	@Mapping(target = "agencyId", source = "agency.id")
    @Mapping(target = "agencyName", source = "agency.name")
	public BranchResponseDto toBrancResponseDto(Branch branch);
	
	@Mapping(target = "agencyId", source = "agency.id")
    @Mapping(target = "agencyName", source = "agency.name")
	public List<BranchResponseDto> toListBranchResponses(List<Branch> branches);
}
