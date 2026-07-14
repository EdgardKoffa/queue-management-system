package com.nsglobal.queue.dashboard.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsglobal.queue.dashboard.dto.BranchStatisticsDto;
import com.nsglobal.queue.dashboard.dto.CounterStatisticsDto;
import com.nsglobal.queue.dashboard.dto.DashboardDto;
import com.nsglobal.queue.dashboard.dto.OperatorStatisticsDto;
import com.nsglobal.queue.dashboard.dto.ServiceStatisticsDto;
import com.nsglobal.queue.dashboard.repository.DashboardRepository;
import com.nsglobal.queue.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class DashboardServiceImpl implements DashboardService {
	
	private final DashboardRepository dashboardRepository;

	@Override
	public DashboardDto getDashboard() {

	        DashboardDto dto = new DashboardDto();

	        dto.setWaitingTickets(
	                dashboardRepository.countWaitingTickets());

	        dto.setCalledTickets(
	                dashboardRepository.countCalledTickets());

	        dto.setCompletedTickets(
	                dashboardRepository.countCompletedTickets());

	        dto.setActiveCounters(
	                dashboardRepository.countOpenCounters());

	        dto.setBusyCounters(
	                dashboardRepository.countBusyCounters());

	        dto.setClosedCounters(
	                dashboardRepository.countClosedCounters());

	        dto.setAverageWaitingTime(
	                dashboardRepository.averageWaitingTime());

	        dto.setAverageServiceTime(
	                dashboardRepository.averageServiceTime());

	        return dto;

	}

	@Override
	public List<BranchStatisticsDto> getBranchStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceStatisticsDto> getServiceStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OperatorStatisticsDto> getOperatorStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CounterStatisticsDto> getCounterStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

}
