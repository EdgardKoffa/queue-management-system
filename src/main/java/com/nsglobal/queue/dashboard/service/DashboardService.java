package com.nsglobal.queue.dashboard.service;

import java.util.List;

import com.nsglobal.queue.dashboard.dto.ActivityDto;
import com.nsglobal.queue.dashboard.dto.BranchStatisticsDto;
import com.nsglobal.queue.dashboard.dto.CounterStatisticsDto;
import com.nsglobal.queue.dashboard.dto.DashboardDto;
import com.nsglobal.queue.dashboard.dto.DashboardResponseDto;
import com.nsglobal.queue.dashboard.dto.HourlyStatisticsDto;
import com.nsglobal.queue.dashboard.dto.OperatorStatisticsDto;
import com.nsglobal.queue.dashboard.dto.RecentTicketDto;
import com.nsglobal.queue.dashboard.dto.ServiceStatisticsDto;

public interface DashboardService {
	
    /**
     * Retourne les resumes globaux du système.
     *
     * @return DashboardDto
     */
	DashboardDto getDashboardSummarry();
	
	/**
     * Retourne les indicateurs globaux du système.
     *
     * @return DashboardResponseDto
     */
    DashboardResponseDto getDashboard();

    /**
     * Retourne les statistiques par agence.
     *
     * @return liste des statistiques des agences
     */
    List<BranchStatisticsDto> getBranchStatistics();

    /**
     * Retourne les statistiques par service bancaire.
     *
     * @return liste des statistiques des services
     */
    List<ServiceStatisticsDto> getServiceStatistics();

    /**
     * Retourne les statistiques des opérateurs.
     *
     * @return liste des statistiques des opérateurs
     */
    List<OperatorStatisticsDto> getOperatorStatistics();

    /**
     * Retourne les statistiques des guichets.
     *
     * @return liste des statistiques des guichets
     */
    List<CounterStatisticsDto> getCounterStatistics();
    
    List<RecentTicketDto> getRecentTickets();
    
    List<ActivityDto> getActivities();
    
    List<HourlyStatisticsDto> getHourlyStatistics();
    
    
    
    DashboardDto summaryTest();

}
