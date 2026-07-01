package com.nsglobal.queue.ticket.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsglobal.queue.bankservice.entity.BankService;
import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.ticket.entity.TicketSequence;

import jakarta.persistence.LockModeType;

public interface TicketSequenceRepository extends JpaRepository<TicketSequence, Long> {
	
	 Optional<TicketSequence> findByBranchAndServiceAndSequenceDate(
	            Branch branch,
	            BankService service,
	            LocalDate sequenceDate);
	 
	 @Lock(LockModeType.PESSIMISTIC_WRITE)
		@Query("""
				SELECT ts
				FROM TicketSequence ts
				WHERE ts.branch = :branch
				AND ts.service = :service
				AND ts.sequenceDate = :sequenceDate
				""")
		 Optional<TicketSequence> findForUpdate(
				 @Param("branch") Branch branch, 
				 @Param("service") BankService service,
		         @Param("sequenceDate") LocalDate sequenceDate
		         );

}
