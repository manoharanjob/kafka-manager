package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class KafkaExecutionException extends RuntimeException {

	private String code;

	public KafkaExecutionException(String code, String message) {
		super(message);
		this.code = code;
	}

}
