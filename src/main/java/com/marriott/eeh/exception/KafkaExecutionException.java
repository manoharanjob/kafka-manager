package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class KafkaExecutionException extends RuntimeException {

	private String code;
	private String additionalInfo;

	public KafkaExecutionException(String code, String message) {
		super(message);
		this.code = code;
		this.additionalInfo = message;
	}

}
