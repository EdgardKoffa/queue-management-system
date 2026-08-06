package com.nsglobal.queue.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsglobal.queue.common.response.ApiErrorResponse;
import com.nsglobal.queue.security.auth.exception.InvalidCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(InvalidCredentialsException.class)
	    public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(
	            InvalidCredentialsException ex) {
		 String msg="Violation de contrainte : %s".formatted((ex.getCause()!=null?ex.getCause().getMessage():ex.getMessage()));
		 
		 ApiErrorResponse erros=ApiErrorResponse
	        		.builder()
	        		.message(msg)
	        		.success(false)
	        		.status(0)
	        		.build();
		 
	        ErrorResponse error = ErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.UNAUTHORIZED.value())
	                .error("Unauthorized")
	                .message(ex.getMessage())
	                .build();
	        
System.out.println("ResponseEntity %s".formatted(ex.getMessage()));

	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(erros);

	    }
	 @ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
	     
		 String msg="Violation de contrainte : %s".formatted(extractMessage(ex));
		 
		 Map<String, String> error = new HashMap<>();
	        error.put("error", "Violation de contrainte : " + extractMessage(ex));
	       
	        ApiErrorResponse erros=ApiErrorResponse
	        		.builder()
	        		.error(msg)
	        		.message(msg)
	        		.success(false)
	        		.status(HttpStatus.CONFLICT.value())
	        		.errors(error)
	        		.build();
	        		
	        return ResponseEntity.status(HttpStatus.CONFLICT).body( erros);
	    }

	    private String extractMessage(DataIntegrityViolationException ex) {
	        if (ex.getCause() != null) {
	            return ex.getCause().getMessage();
	        }
	        return ex.getMessage();
	    }
}
