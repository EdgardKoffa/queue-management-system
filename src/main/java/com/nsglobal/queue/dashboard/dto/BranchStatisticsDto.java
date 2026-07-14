package com.nsglobal.queue.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchStatisticsDto {
	
	 /**
     * Identifiant de l'agence.
     */
    private Long branchId;

    /**
     * Nom de l'agence.
     */
    private String branch;

    /**
     * Tickets en attente.
     */
    private Long waitingTickets;

    /**
     * Tickets en cours.
     */
    private Long calledTickets;

    /**
     * Tickets terminés.
     */
    private Long completedTickets;

    /**
     * Guichets ouverts.
     */
    private Long openCounters;

    /**
     * Guichets occupés.
     */
    private Long busyCounters;

    /**
     * Guichets fermés.
     */
    private Long closedCounters;

    /**
     * Temps moyen d'attente.
     */
    private Double averageWaitingTime;

    /**
     * Temps moyen de traitement.
     */
    private Double averageServiceTime;

}
