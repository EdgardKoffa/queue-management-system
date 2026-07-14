package com.nsglobal.queue.scheduler.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
@Component
@ConfigurationProperties(prefix = "com.nsglobal.queue")
public class SchedulerProperties {
	
	  /**
     * Réinitialisation quotidienne des séquences.
     */
    private String ticketSequenceResetCron;

    /**
     * Fermeture automatique des guichets.
     */
    private String counterCloseCron;

    /**
     * Ouverture automatique des guichets.
     */
    private String counterOpenCron;

    /**
     * Rafraîchissement automatique du dashboard.
     */
    private String dashboardRefreshCron;

    /**
     * Vérification des tickets absents.
     */
    private String absentTicketCron;

    /**
     * Nettoyage des anciennes données.
     */
    private String cleanupCron;
    
    /**
     * Nettoyage des anciennes données.
     */
    private List<String> countersToOpenCron;
    
    /**
     * Temps avant qu'un ticket soit considéré absent.
     */
    private Integer absentDelayMinutes = 5;

    /**
     * Nombre de jours avant suppression des historiques.
     */
    private Integer historyRetentionDays = 90;

    /**
     * Nombre de jours avant suppression des tickets annulés.
     */
    private Integer cancelledTicketRetentionDays = 30;
    
}
