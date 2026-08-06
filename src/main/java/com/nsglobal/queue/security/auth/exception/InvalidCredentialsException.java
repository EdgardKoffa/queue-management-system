package com.nsglobal.queue.security.auth.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InvalidCredentialsException extends RuntimeException {

	  private static final long serialVersionUID = 1L;
	  private final HttpStatus status;


	 
	  
	 
	    public InvalidCredentialsException(String message) {
	        super(message);
	        this.status = HttpStatus.UNAUTHORIZED; // Valeur par défaut (401)
	    }

	    public InvalidCredentialsException(String message, HttpStatus status) {
	        super(message);
	        this.status = status;
	    }

	    public HttpStatus getStatus() {
	        return status;
	    }

}
