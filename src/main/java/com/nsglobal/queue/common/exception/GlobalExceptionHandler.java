package com.nsglobal.queue.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nsglobal.queue.security.auth.exception.InvalidCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(InvalidCredentialsException.class)
	    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
	            InvalidCredentialsException ex) {

	        ErrorResponse error = ErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.UNAUTHORIZED.value())
	                .error("Unauthorized")
	                .message(ex.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(error);

	    }
}
