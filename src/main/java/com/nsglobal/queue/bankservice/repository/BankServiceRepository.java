package com.nsglobal.queue.bankservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.dashboard.dto.ServiceStatisticsDto;

public interface BankServiceRepository extends JpaRepository<BankService, Long> {
	
	@EntityGraph(attributePaths = {"branch"})
	List<BankService> findAll();
	
	@EntityGraph(attributePaths = {"branch"})
	Optional<BankService> findById(Long id);
	
	//List<ServiceStatisticsDto> getServiceStatistics();
}
