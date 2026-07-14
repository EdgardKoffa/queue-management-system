package com.nsglobal.queue.dashboard.repository;

import org.springframework.stereotype.Repository;

import com.nsglobal.queue.branch.repository.BranchRepository;
import com.nsglobal.queue.common.enums.CounterStatus;
import com.nsglobal.queue.common.enums.TicketStatus;
import com.nsglobal.queue.counter.repository.CounterRepository;
import com.nsglobal.queue.ticket.repository.TicketHistoryRepository;
import com.nsglobal.queue.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

	private final TicketRepository ticketRepository;

	private final CounterRepository counterRepository;

	private final TicketHistoryRepository historyRepository;
	
	private final BranchRepository branchRepository;
	
	
	/*
	 * @return nombre de tiket global en attente
	 *  */
	 public Long countWaitingTickets() {
	        return ticketRepository.countByStatus(TicketStatus.WAITING);
	    }

	 /*
		 * @return nombre de tiket global en encours
		 *  */
	    public Long countCalledTickets() {
	        return ticketRepository.countByStatus(TicketStatus.IN_PROGRESS);
	    }

	    /*
		 * @return nombre de tiket global en fini
		 *  */
	    public Long countCompletedTickets() {
	        return ticketRepository.countByStatus(TicketStatus.COMPLETED);
	    }
	    
//==================guichet===================================
	    /*
		 * @return nombre global de guichet  disponible
		 *  */
	    public Long countOpenCounters() {
	        return counterRepository.countByStatus(CounterStatus.OPEN);
	    }

	    /*
		 * @return nombre global de guichet  occuper
		 *  */
	    public Long countBusyCounters() {
	        return counterRepository.countByStatus(CounterStatus.BUSY);
	    }

	    /*
		 * @return nombre global de guichet  fermer
		 *  */
	    public Long countClosedCounters() {
	        return counterRepository.countByStatus(CounterStatus.CLOSED);
	    }
	    
	    /*
		 * @return moyenne  global de temps d'attente 
		 *  */
	    public Double averageWaitingTime() {
	        return 0.0;// historyRepository.averageWaitingTime();
	    }

	    /*
		 * @return moyyene global de temps d'operation ou de service  
		 *  */
	    public Double averageServiceTime() {
	        return 0.0;// historyRepository.averageServiceTime();
	    }
	    
	 //==========statistics du jour encours dans une agence (brance)==========
	    
	    
	 
	//==========statistics du jour encours dans une agence(brance) par guichet(counter) ==========
	    
//==========statistics du jour encour dans une agence (branch) par service (bankservice)==========    
}
