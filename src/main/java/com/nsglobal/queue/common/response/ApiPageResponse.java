package com.nsglobal.queue.common.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter

public class ApiPageResponse<T> /* extends ApiResponse<List<T>> */{
	 
	private boolean success;

	    private String message;
		
	    private List<T> data;
	    
	    private int page;

	    private int size;

	    private long totalElements;

	    private int totalPages;

	    private boolean first;

	    private boolean last;

	    @Builder.Default
	   private LocalDateTime timestamp = LocalDateTime.now();


}
