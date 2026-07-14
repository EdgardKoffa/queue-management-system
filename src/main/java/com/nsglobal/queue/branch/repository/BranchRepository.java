package com.nsglobal.queue.branch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.dashboard.dto.BranchStatisticsDto;

public interface BranchRepository extends JpaRepository<Branch, Long> {
	
	@EntityGraph(attributePaths = {"agency"})
	    List<Branch> findAll();
	 
	 @EntityGraph(attributePaths = {"agency"})
	 Optional<Branch> findById(Long id);
	 
		 
}
