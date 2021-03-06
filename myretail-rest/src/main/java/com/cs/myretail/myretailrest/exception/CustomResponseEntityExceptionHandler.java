package com.cs.myretail.myretailrest.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public final ResponseEntity<CustomExceptionResponse> handleUserNotFoundException(ProductNotFoundException ex) {
		return new ResponseEntity<>(new CustomExceptionResponse(new Date(), ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CustomExceptionResponse> handleExceptions(Exception ex) {
		return new ResponseEntity<>(new CustomExceptionResponse(new Date(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
