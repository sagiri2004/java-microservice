package com.microserviceproject.commonservice.advice;

import com.microserviceproject.commonservice.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleException(Exception ex) {
		return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), "9999", HttpStatus.INTERNAL_SERVER_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
