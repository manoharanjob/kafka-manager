package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class TopicExecutionException extends RuntimeException {

	private String code;

	public TopicExecutionException(String code, String message) {
		super(message);
		this.code = code;
	}

}
