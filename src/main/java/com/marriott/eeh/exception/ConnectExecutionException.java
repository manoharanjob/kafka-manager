package com.marriott.eeh.exception;

import lombok.Getter;

@Getter
public class ConnectExecutionException extends RuntimeException {

	private String code;

	public ConnectExecutionException(String code, String message) {
		super(message);
		this.code = code;
	}

}
