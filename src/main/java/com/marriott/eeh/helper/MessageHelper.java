package com.marriott.eeh.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	public String get(String code) {
		return messageSourceAccessor.getMessage(code);
	}

	public String getCode(String code) {
		return messageSourceAccessor.getMessage(String.format("%s.code", code), code);
	}

	public String getTitle(String code) {
		return messageSourceAccessor.getMessage(String.format("%s.title", code), code);
	}

	public String getMessage(String code) {
		return messageSourceAccessor.getMessage(String.format("%s.message", code), code);
	}
}
