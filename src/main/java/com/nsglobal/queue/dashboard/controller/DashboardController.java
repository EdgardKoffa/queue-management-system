package com.nsglobal.queue.dashboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsglobal.queue.common.constant.ApiRoutes;
import com.nsglobal.queue.common.constant.HasPermissions;
import com.nsglobal.queue.common.response.ApiResponse;
import com.nsglobal.queue.common.response.ResponseBuilder;
import com.nsglobal.queue.dashboard.dto.BranchStatisticsDto;
import com.nsglobal.queue.dashboard.dto.CounterStatisticsDto;
import com.nsglobal.queue.dashboard.dto.DashboardDto;
import com.nsglobal.queue.dashboard.dto.DashboardResponseDto;
import com.nsglobal.queue.dashboard.dto.OperatorStatisticsDto;
import com.nsglobal.queue.dashboard.dto.ServiceStatisticsDto;
import com.nsglobal.queue.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.DASHBOARD)
@RequiredArgsConstructor
public class DashboardController {
	
	 private final DashboardService dashboardService;
	 
	// @PreAuthorize(HasPermissions.HAS_VIEW_DASHBOARD)
	 @GetMapping
	    public ResponseEntity<ApiResponse<DashboardResponseDto>> dashboard() {
	       
		 return ResponseEntity.ok(
	    			ResponseBuilder.success("Données recupérées",
	    					dashboardService.getDashboard())
	    			);
	    }
	 
	 @GetMapping("/summary")
	    public ResponseEntity<ApiResponse<DashboardDto>> dashboardSummary() {
	       
		 return ResponseEntity.ok(
	    			ResponseBuilder.success("Données recupérées",
	    					dashboardService.getDashboardSummarry())
	    			);
	    }

	    @GetMapping("/branches")
	    public ResponseEntity<ApiResponse<List<BranchStatisticsDto>>> branches() {
	        return ResponseEntity.ok(
	    			ResponseBuilder.success("Données recupérées",
	        		dashboardService.getBranchStatistics()));
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
	    
	    @GetMapping("/test/summary")
	    public ResponseEntity<ApiResponse<DashboardDto>> summary() {
	    	
	    	DashboardDto resp=dashboardService.summaryTest();
	    	
	    	return ResponseEntity.ok(
	    			ResponseBuilder.success("Données recupérées",resp)
	    			);
	    }


}
