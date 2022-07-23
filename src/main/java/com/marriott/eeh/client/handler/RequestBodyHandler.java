package com.marriott.eeh.client.handler;

import java.net.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class RequestBodyHandler {

	private static final Logger log = LoggerFactory.getLogger(RequestBodyHandler.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.findAndRegisterModules();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public static HttpRequest.BodyPublisher ofBodyPublisher(Object requestBody) {
		try {
			return HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody));
		} catch (JsonProcessingException e) {
			log.error("");
		}
		return null;
	}

}
