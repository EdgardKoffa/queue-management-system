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
public class DashboardDto {
	
	/**
     * Tickets actuellement en attente.
     */
    private Long waitingTickets;

    /**
     * Tickets actuellement appelés
     * (statut IN_PROGRESS).
     */
    private Long calledTickets;

    /**
     * Tickets terminés.
     */
    private Long completedTickets;

    /**
     * Nombre de guichets ouverts.
     */
    private Long activeCounters;

    /**
     * Nombre de guichets occupés.
     */
    private Long busyCounters;

    /**
     * Nombre de guichets fermés.
     */
    private Long closedCounters;

    /**
     * Temps moyen d'attente
     * en minutes.
     */
    private Double averageWaitingTime;

    /**
     * Temps moyen de traitement
     * en minutes.
     */
    private Double averageServiceTime;

}
