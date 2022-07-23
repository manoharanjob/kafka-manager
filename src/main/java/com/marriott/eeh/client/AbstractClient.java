package com.marriott.eeh.client;

import java.net.URI;
import java.net.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marriott.eeh.client.handler.RequestBodyHandler;

public class AbstractClient {

	private final Logger log = LoggerFactory.getLogger(AbstractClient.class);
	
	private String[] headers = new String[] { "Content-Type", "application/json", "Accept", "application/json" };

	public String[] getHeaders() {
		return headers;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public HttpRequest getHttpRequest(String url) {
		return HttpRequest.newBuilder(URI.create(url))
				.GET()
				.headers(getHeaders())
				.build();
	}

	/**
	 * @param url
	 * @return
	 */
	public HttpRequest postHttpRequest(String url, Object requestBody) {
		return HttpRequest.newBuilder(URI.create(url))
				.POST(RequestBodyHandler.ofBodyPublisher(requestBody))
				.headers(getHeaders())
				.build();
	}

	/**
	 * @param url
	 * @return
	 */
	public HttpRequest putHttpRequest(String url, Object requestBody) {
		return HttpRequest.newBuilder(URI.create(url))
				.PUT(RequestBodyHandler.ofBodyPublisher(requestBody))
				.headers(getHeaders())
				.build();
	}

	/**
	 * @param url
	 * @return
	 */
	public HttpRequest patchHttpRequest(String url, Object requestBody) {
		return HttpRequest.newBuilder(URI.create(url))
				.method("PATCH",RequestBodyHandler.ofBodyPublisher(requestBody))
				.headers(getHeaders())
				.build();
	}

	/**
	 * @param url
	 * @return
	 */
	public HttpRequest deleteHttpRequest(String url) {
		return HttpRequest.newBuilder(URI.create(url))
				.DELETE()
				.headers(getHeaders())
				.build();
	}
	
}
