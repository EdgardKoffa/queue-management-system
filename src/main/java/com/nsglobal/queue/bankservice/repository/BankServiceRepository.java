package com.nsglobal.queue.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.bankservice.entity.BankService;

public interface BankServiceRepository extends JpaRepository<BankService, Long> {

}
