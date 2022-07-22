package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class SchemaExecutionException extends RuntimeException {

	private String code;

	public SchemaExecutionException(String code, String message) {
		super(message);
		this.code = code;
	}

}
