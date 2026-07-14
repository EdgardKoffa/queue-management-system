package com.nsglobal.queue.ticket.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nsglobal.queue.branch.entity.Branch;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	// Strategy PRIORITY
	@EntityGraph(attributePaths = {"branch","service","counter"})
	Optional<Ticket> findFirstByBranchAndStatusOrderByPriorityDescIssueTimeAsc(
	        Branch branch,
	        TicketStatus status
	);
	
	//strategy Fifo
	@EntityGraph(attributePaths = {"branch","service","counter"})
	Optional<Ticket> findFirstByBranchAndStatusOrderByIssueTimeAsc(Branch branch, TicketStatus status);
	
	boolean existsByCounterIdAndStatus(Long counterId,TicketStatus status);
	
	@EntityGraph(attributePaths = {"branch","service","counter"})
	Optional<Ticket> findByCounterIdAndStatus(Long counterId,TicketStatus status);
	/*
	 * list de ticket par status et date de ticket ordonner par le plus ancien
	 * */
	List<Ticket> findByStatusAndTicketDateOrderByStartTimeAsc(TicketStatus status,LocalDate today);
	
	Ticket findByIdAndStatus(Long id,TicketStatus status);
	
	/*
	 * dashbord functions
	 * **/
	Long countByStatus(TicketStatus status);

	Long countByCounterIdAndStatus(Long counterId, TicketStatus status);

	 Long countByBranchIdAndStatus(Long branchId, TicketStatus status);
	 
	 @EntityGraph(attributePaths = {"branch","service","counter"})
	 List<Ticket> findByStatus(TicketStatus status);
	 
	 Long countByBranchAndServiceAndStatus(
            Long branchId,
            Long serviceId,
            TicketStatus status
            );


/*====================requetes pour alimenter le dashboard========================*/
	 
	 @Query("""
			 SELECT COUNT(t)
			 FROM Ticket t
			 WHERE t.branch.id=:branchId
			 AND t.status=:status
			 AND t.ticketDate=:today
			 """)
			 Long countTicketStatusByBranch(Long branchId,TicketStatus status,LocalDate today);
	
	 
	 @Query("""
			 SELECT COUNT(t)
			 FROM Ticket t
			 WHERE t.counter.id=:counterId
			 AND t.status=:status
			 """)
			 Long countTicketStatusByCounter(Long counterId,TicketStatus status);
	 
	 /*
	  * lister les tickets a la date =aujoudhui d'une agence(branch) a un statut(cree, appeller , fini, anuller....)
	  * */
	 @EntityGraph(attributePaths = {"branch","service","counter"})
	 List<Ticket> findByStatusAndTicketDateAndBranch(Long branchId,LocalDate today,TicketStatus status);
	 
	 /*
	  * le ticket a la date =aujoudhui d'un guichet(branch) a un statut(cree, appeller , fini, anuller....)
		
	  * */
	 @EntityGraph(attributePaths = {"branch","service","counter"})
	 Ticket findByStatusAndTicketDateAndCounter(Long counterId,LocalDate today,TicketStatus status);




}
