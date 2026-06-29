package com.nsglobal.queue.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.branch.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
