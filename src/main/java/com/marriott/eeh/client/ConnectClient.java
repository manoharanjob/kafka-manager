package com.marriott.eeh.client;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectClient extends AbstractClient {

	private final Logger log = LoggerFactory.getLogger(ConnectClient.class);

	@Autowired
	private HttpClient connectHttpClient;

	public HttpResponse<String> getConnectors() throws IOException, InterruptedException {
		return connectHttpClient.send(getHttpRequest("/connectors"), BodyHandlers.ofString());
	}

}
