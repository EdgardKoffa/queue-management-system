package com.nsglobal.queue.common.response;

import org.springframework.data.domain.Page;

public final class ResponseBuilder {
	
	public ResponseBuilder() {
		
	}
	public static <T> ApiResponse<T> success(
	        String message,
	        T data) {
	  return ApiResponse.<T>builder()
	            .success(true)
	            .message(message)
	            .data(data)
	            .build();

	}
	
	public static <T> ApiResponse<T> error(
	        String message) {
		
	  return ApiResponse.<T>builder()
	            .success(false)
	            .message(message)
	            .data(null)
	            .build();

	}
	
	public static ApiResponse<Void> success(
	        String message) {

	    return ApiResponse.<Void>builder()
	            .success(true)
	            .message(message)
	            .build();

	}

	
	public static <T> ApiPageResponse<T> page(
	        String message,
	        Page<T> page){
		  
		return ApiPageResponse.<T>builder()
		        .success(true)
		        .message(message)
		        .data(page.getContent())
		        .page(page.getNumber())
		        .size(page.getSize())
		        .totalPages(page.getTotalPages())
		        .totalElements(page.getTotalElements())
		        .first(page.isFirst())
		        .last(page.isLast())
		        .build();

		
	}
	
}
