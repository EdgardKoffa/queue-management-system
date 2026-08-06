package com.nsglobal.queue.dashboard.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDto {
	
	private DashboardDto summary;
	
	private List<CounterStatisticsDto> counterStatistcs;
	
	private List<OperatorStatisticsDto> operatorStatistics;
	
    private List<HourlyStatisticsDto> hourlyStatistics;

    private List<ServiceStatisticsDto> serviceStatistics;

    private List<RecentTicketDto> recentTickets;

    private List<ActivityDto> recentActivities;
}
