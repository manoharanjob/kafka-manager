package com.marriott.eeh.client;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marriott.eeh.configuration.KafkaProperties;

@Component
public class ConnectClient extends AbstractClient {

	private final Logger log = LoggerFactory.getLogger(ConnectClient.class);

//	@Autowired
	private HttpClient connectHttpClient;

	@Autowired
	KafkaProperties kafkaProps;
	
	private static String baseUrl;

	@PostConstruct
	public void init() {
		baseUrl = kafkaProps.getDevConnect().get("connect.server.url");
	}

	public CompletableFuture<HttpResponse<String>> getConnectors() throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(getHttpRequest(baseUrl + "/connectors"), BodyHandlers.ofString());
	}

	public CompletableFuture<HttpResponse<String>> getConnector(String connector)
			throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(getHttpRequest(baseUrl + "/connectors/" + connector),
				BodyHandlers.ofString());
	}

	public CompletableFuture<HttpResponse<String>> createConnector(Map<String, Object> requestBody)
			throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(postHttpRequest(baseUrl + "/connectors", requestBody),
				BodyHandlers.ofString());
	}

	public CompletableFuture<HttpResponse<String>> deleteConnector(String connector)
			throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(deleteHttpRequest(baseUrl + "/connectors/" + connector),
				BodyHandlers.ofString());
	}

}
