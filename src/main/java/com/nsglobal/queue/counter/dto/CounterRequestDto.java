package com.nsglobal.queue.counter.dto;

import com.nsglobal.queue.common.enums.CounterStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CounterRequestDto {
	
		@NotBlank
		@Size(max = 20)
		private String code;
		
		@NotNull
	    private Integer number;
		
		@NotBlank
		@Size(max = 100)
	    private String name;
		
		@NotBlank
	    private CounterStatus status;
		
		@NotNull
	    private Boolean active ;
		
		@NotNull
	    private Long branchId;
}
