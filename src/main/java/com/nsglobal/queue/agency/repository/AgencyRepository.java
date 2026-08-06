package com.nsglobal.queue.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.agency.entity.Agency;
import com.nsglobal.queue.common.enums.EnumStatus;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
	
	@Modifying
    @Transactional // Requis pour les requêtes d'écriture (UPDATE / DELETE)
    @Query("UPDATE Agency a SET a.status = :status WHERE a.id = :id ")
    int updateStatusById(@Param("status") EnumStatus status, @Param("id") Long id);
}
