package com.nsglobal.queue.branch.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.enums.EnumStatus;

public interface BranchRepository extends JpaRepository<Branch, Long> {
	
	@EntityGraph(attributePaths = {"agency"})
	    Page<Branch> findAll(Pageable page);
	 
	 @EntityGraph(attributePaths = {"agency"})
	 Optional<Branch> findById(Long id);
	 
	 @Modifying
    @Transactional // Requis pour les requêtes d'écriture (UPDATE / DELETE)
    @Query(nativeQuery = true, value = "UPDATE Branch b SET b.status = :status WHERE b.id = :id RETURNING * ")
    Optional<Branch> updateStatusById(@Param("status") EnumStatus status, @Param("id") Long id);
	 
		 
}
