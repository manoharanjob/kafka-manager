package com.marriott.eeh.client;

import java.net.URI;
import java.net.http.HttpRequest;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.marriott.eeh.client.handler.RequestBodyHandler;
import com.marriott.eeh.constant.Env;

public class AbstractClient {

	private final Logger log = LoggerFactory.getLogger(AbstractClient.class);
	
	private String[] headers = new String[] { "Content-Type", "application/json", "Accept", "application/json" };

	@Autowired
	private AdminClient devAdminClient;

	@Autowired
	private AdminClient testAdminClient;

	@Autowired
	private AdminClient perfAdminClient;

	protected AdminClient getKafkaClient(Env env) {
		switch (env) {
		case dev:
			return devAdminClient;
		case test:
			return testAdminClient;
		case perf:
			return perfAdminClient;
		default:
			return devAdminClient;
		}
	}
	
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
