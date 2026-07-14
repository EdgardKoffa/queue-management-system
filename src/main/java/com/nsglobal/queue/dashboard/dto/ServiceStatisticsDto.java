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
public class ServiceStatisticsDto {
	

    /**
     * Identifiant du service.
     */
    private Long serviceId;

    /**
     * Nom du service.
     */
    private String service;

    /**
     * Nombre total de tickets.
     */
    private Long totalTickets;

    /**
     * Temps moyen d'attente.
     */
    private Double averageWaitingTime;

    /**
     * Temps moyen de traitement.
     */
    private Double averageServiceTime;


}
