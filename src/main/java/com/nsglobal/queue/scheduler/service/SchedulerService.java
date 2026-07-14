package com.nsglobal.queue.scheduler.service;

public interface SchedulerService {
	
    /**
     * Ouvre automatiquement les guichets.
     */
    void openCounters();

    /**
     * Ferme automatiquement les guichets.
     */
    void closeCounters();

    /**
     * Met à jour le Dashboard.
     */
    void refreshDashboard();

    /**
     * Détecte les tickets absents.
     */
    void processAbsentTickets();

    /**
     * Nettoyage automatique.
     */
    void cleanup();
}
