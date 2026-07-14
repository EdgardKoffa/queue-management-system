package com.nsglobal.queue.counter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.counter.entity.CounterHistory;

public interface CounterHistoryRepository extends JpaRepository<CounterHistory, Long> {
	
	@EntityGraph(attributePaths = {"counter"})
 List<CounterHistory> findAll();
 
	@EntityGraph(attributePaths = {"counter"})
 Optional<CounterHistory> findById(Long id);

}
