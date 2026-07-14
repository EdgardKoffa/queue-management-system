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
public class CounterStatisticsDto {
	
	 /**
     * Identifiant du guichet.
     */
    private Long counterId;

    /**
     * Nom ou numéro du guichet.
     */
    private String counter;

    /**
     * Nom de l'agence.
     */
    private String branch;

    /**
     * Opérateur affecté.
     */
    private String operator;

    /**
     * Statut du guichet.
     */
    private String status;

    /**
     * Nombre de tickets traités aujourd'hui.
     */
    private Long completedTickets;

    /**
     * Temps moyen de traitement.
     */
    private Double averageServiceTime;

}
