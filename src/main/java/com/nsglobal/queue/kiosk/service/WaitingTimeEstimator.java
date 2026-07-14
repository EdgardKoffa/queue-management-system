package com.nsglobal.queue.kiosk.service;

public interface WaitingTimeEstimator {
	
	 Integer estimate(Long branchId,
             Long serviceId);

}
