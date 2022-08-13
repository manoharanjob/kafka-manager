package com.marriott.eeh.exception.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marriott.eeh.exception.model.ErrorInfo;
import com.marriott.eeh.exception.model.ErrorResponse;
import com.marriott.eeh.helper.EnumHelper;
import com.marriott.eeh.helper.MessageHelper;

public class AbstractExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(AbstractExceptionHandler.class);

	@Autowired
	protected MessageHelper messageHelper;
	
	@Autowired
	private EnumHelper enumHelper;

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse("error.request.invalid.request",
				"method", e.getMethod(), String.join(", ", e.getSupportedMethods())));
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildErrorResponse("error.request.invalid.request", "param", e.getParameterName()));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse("error.request.invalid.request",
				e.getMessage().contains("JSON parse error") ? "ParseError" : "body"));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Collection<ErrorInfo> errors = e.getBindingResult()
										.getFieldErrors()
										.stream()
										.map(error -> {
											String code = error.getCode();
											return ErrorInfo.builder()
													.code(messageHelper.getCode("error.validation.constraints"))
													.title(messageHelper.getTitle("error.validation.constraints"))
													.message(messageHelper.getMessage("error.validation.constraints." + code, error.getField(), error.getRejectedValue(), enumHelper.getNames(code)))
													.build();
										})
										.collect(Collectors.toList());
		if (e.getGlobalErrorCount() > 0) {
			errors.addAll(e.getBindingResult()
							.getGlobalErrors()
							.stream()
							.map(error -> ErrorInfo.builder()
									.code(messageHelper.getCode(error.getDefaultMessage()))
									.title(messageHelper.getTitle(error.getDefaultMessage()))
									.message(messageHelper.getMessage(error.getDefaultMessage()))
									.build())
							.collect(Collectors.toList()));
		}
		return ResponseEntity.status(status)
				.body(ErrorResponse.builder()
						.errors(errors)
						.build());
	}
	
	protected ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus httpStatus, ConstraintViolationException e) {
		Collection<ErrorInfo> errors = e.getConstraintViolations()
										.stream()
										.map(violation -> ErrorInfo.builder()
												.code(messageHelper.getCode("error.validation.constraints"))
												.title(messageHelper.getTitle("error.validation.constraints"))
												.message(messageHelper.getMessage("error.validation.constraints." + violation.getMessage()))
												.build())
										.collect(Collectors.toList());
		return ResponseEntity.status(httpStatus)
				.body(ErrorResponse.builder()
						.errors(errors)
						.build());
	}
	
	protected ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus httpStatus, String code) {
		return ResponseEntity.status(httpStatus)
				.body(buildErrorResponse(code));
	}

	protected ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus httpStatus, String code, String message) {
		return ResponseEntity.status(httpStatus)
				.body(buildErrorResponse(code));
	}
	
	protected ErrorResponse buildErrorResponse(String code) {
		Collection<ErrorInfo> errors = new ArrayList<>();
		errors.add(ErrorInfo.builder()
				.code(messageHelper.getCode(code))
				.title(messageHelper.getTitle(code))
				.message(messageHelper.getMessage(code))
				.build());
		return ErrorResponse.builder()
				.errors(errors)
				.build();
	}

	protected ErrorResponse buildErrorResponse(String code, String message) {
		Collection<ErrorInfo> errors = new ArrayList<>();
		errors.add(ErrorInfo.builder()
				.code(messageHelper.getCode(code))
				.title(messageHelper.getTitle(code))
				.message(messageHelper.getMessage(code + "." + message))
				.build());
		return ErrorResponse.builder()
				.errors(errors)
				.build();
	}
	
	protected ErrorResponse buildErrorResponse(String code, String message, Object... args) {
		Collection<ErrorInfo> errors = new ArrayList<>();
		errors.add(ErrorInfo.builder()
				.code(messageHelper.getCode(code))
				.title(messageHelper.getTitle(code))
				.message(messageHelper.getMessage(code + "." + message, args))
				.build());
		return ErrorResponse.builder()
				.errors(errors)
				.build();
	}
}
