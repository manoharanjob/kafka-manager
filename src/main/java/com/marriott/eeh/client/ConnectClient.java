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

import com.marriott.eeh.configuration.KafkaConfiguration;

@Component
public class ConnectClient extends AbstractClient {

	private final Logger log = LoggerFactory.getLogger(ConnectClient.class);

	@Autowired
	private HttpClient connectHttpClient;

	private static String baseUrl;

	@PostConstruct
	public void init() {
		baseUrl = KafkaConfiguration.getConnectConfig().get("connect.server.url");
	}

	public CompletableFuture<HttpResponse<String>> getConnectors() throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(getHttpRequest("/connectors"), BodyHandlers.ofString());
	}

	public CompletableFuture<HttpResponse<String>> getConnector(String connector)
			throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(getHttpRequest("/connectors/" + connector), BodyHandlers.ofString());
	}

	public CompletableFuture<HttpResponse<String>> createConnector(Map<String, Object> requestBody)
			throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(postHttpRequest("/connectors", requestBody), BodyHandlers.ofString());
	}

	public CompletableFuture<HttpResponse<String>> deleteConnector(String connector)
			throws IOException, InterruptedException {
		return connectHttpClient.sendAsync(deleteHttpRequest("/connectors/" + connector), BodyHandlers.ofString());
	}

}
