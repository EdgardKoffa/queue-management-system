package com.nsglobal.queue.dashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.dashboard.dto.BranchStatisticsDto;
import com.nsglobal.queue.dashboard.dto.CounterStatisticsDto;
import com.nsglobal.queue.dashboard.dto.DashboardDto;
import com.nsglobal.queue.dashboard.dto.OperatorStatisticsDto;
import com.nsglobal.queue.dashboard.dto.ServiceStatisticsDto;
import com.nsglobal.queue.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.DASHBOARD)
@RequiredArgsConstructor
public class DashboardController {
	
	 private final DashboardService dashboardService;
	 
	 @GetMapping
	    public DashboardDto dashboard() {
	        return dashboardService.getDashboard();
	    }

	    @GetMapping("/branches")
	    public List<BranchStatisticsDto> branches() {
	        return dashboardService.getBranchStatistics();
	    }

	    @GetMapping("/services")
	    public List<ServiceStatisticsDto> services() {
	        return dashboardService.getServiceStatistics();
	    }

	    @GetMapping("/operators")
	    public List<OperatorStatisticsDto> operators() {
	        return dashboardService.getOperatorStatistics();
	    }

	    @GetMapping("/counters")
	    public List<CounterStatisticsDto> counters() {
	        return dashboardService.getCounterStatistics();
	    }


}
