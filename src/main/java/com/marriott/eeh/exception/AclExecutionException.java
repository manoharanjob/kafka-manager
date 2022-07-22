package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class AclExecutionException extends RuntimeException {

	private String code;

	public AclExecutionException(String code, String message) {
		super(message);
		this.code = code;
	}

}
