package com.nsglobal.queue.common.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiErrorResponse {
	 private boolean success;

	    private int status;

	    private String error;

	    private String message;

	    private String path;

	    private Map<String, String> errors;

	    @Builder.Default
	    private LocalDateTime timestamp = LocalDateTime.now();

}
