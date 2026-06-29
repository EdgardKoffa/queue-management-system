package com.nsglobal.queue.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsglobal.queue.agency.entity.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Long> {

}
