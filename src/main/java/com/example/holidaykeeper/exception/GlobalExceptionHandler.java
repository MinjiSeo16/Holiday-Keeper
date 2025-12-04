package com.example.holidaykeeper.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> handleCustomException(CustomException exception) {
		return ResponseEntity
			.status(exception.getResponseCode().getStatus())
			.body(exception.getResponseCode().getMessage());
	}
}
