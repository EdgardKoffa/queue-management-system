package com.nsglobal.queue.counter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.counter.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Long> {

}
