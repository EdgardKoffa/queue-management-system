package com.nsglobal.queue.counter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.counter.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Long> {
	
	@EntityGraph(attributePaths = {"branch","operator"})
	List<Counter> findAll();
	
	@EntityGraph(attributePaths = {"branch","operator"})
	Optional<Counter> findById(Long id);
	
	@EntityGraph(attributePaths = {"branch","operator"})
	 Optional<Counter> findByOperatorId(Long operatorId);
	
	boolean existsByOperatorId(Long operatorId);
	
	@EntityGraph(attributePaths = {"branch","operator"})
	 Optional<Counter> findByIdAndBranchIdAndStatus(Long id,Long branchId,CounterStatus status);
	
	Long countByStatus(CounterStatus status);
	
	Long countOpenByBranch(Long branchId);
	
	List<Counter> findAllByActive(boolean active);
}
