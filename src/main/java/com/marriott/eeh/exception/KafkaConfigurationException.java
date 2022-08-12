package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class KafkaConfigurationException extends RuntimeException {

	private String message;

	public KafkaConfigurationException(String message) {
		super(message);
		this.message = message;
	}

}
