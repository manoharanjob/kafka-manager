package com.marriott.eeh.exception.handler;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.marriott.eeh.exception.TopicExecutionException;
import com.marriott.eeh.exception.model.ErrorResponse;

@ControllerAdvice
public class KafkaExceptionHandler extends AbstractExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(KafkaExceptionHandler.class);

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse("error.request.invalid.request"));
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e,
			WebRequest request) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(TopicExecutionException.class)
	public ResponseEntity<ErrorResponse> handleTopicExecutionException(TopicExecutionException e, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(e.getCode()));
	}

}
