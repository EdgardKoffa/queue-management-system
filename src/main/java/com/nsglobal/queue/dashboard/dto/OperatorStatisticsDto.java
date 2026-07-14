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
public class OperatorStatisticsDto {
	
	 /**
     * Identifiant de l'opérateur.
     */
    private Long operatorId;

    /**
     * Nom complet.
     */
    private String operator;

    /**
     * Agence.
     */
    private String branch;

    /**
     * Guichet actuel.
     */
    private String counter;

    /**
     * Tickets terminés.
     */
    private Long completedTickets;

    /**
     * Temps moyen d'attente.
     */
    private Double averageWaitingTime;

    /**
     * Temps moyen de traitement.
     */
    private Double averageServiceTime;

}
