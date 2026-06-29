package com.nsglobal.queue.counter.dto;

import java.time.LocalDateTime;

import com.nsglobal.queue.common.enums.CounterStatus;

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
public class CounterResponseDto {
	
		private Long id;

	    private String code;


	    private Integer number;

	    private String name;

	    private CounterStatus status;

	    private Boolean active ;

	    private Long branchId;
	    
	    private String branchName;
	    
	    private LocalDateTime createdAt;
	    
}
