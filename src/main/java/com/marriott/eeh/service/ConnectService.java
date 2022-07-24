package com.marriott.eeh.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marriott.eeh.client.ConnectClient;
import com.marriott.eeh.exception.KafkaExecutionException;

@Service
public class ConnectService {

	private final Logger log = LoggerFactory.getLogger(ConnectService.class);

	@Autowired
	private ConnectClient connectClient;
	@Autowired
	private ObjectMapper objectMapper;

	public String getConnectors() throws KafkaExecutionException {
		try {
			return connectClient.getConnectors().thenApply(body -> body.body()).get();
		} catch (InterruptedException | ExecutionException | IOException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Map getConnector(String connector) throws KafkaExecutionException {
		try {
			return objectMapper.readValue(connectClient.getConnector(connector).thenApply(body -> body.body()).get(),
					Map.class);
		} catch (InterruptedException | ExecutionException | IOException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Map createConnector(Map<String, Object> requestBody) throws KafkaExecutionException {
		try {
			return objectMapper.readValue(
					connectClient.createConnector(requestBody).thenApply(body -> body.body()).get(), Map.class);
		} catch (InterruptedException | ExecutionException | IOException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public String deleteConnector(String connector) throws KafkaExecutionException {
		try {
			return connectClient.deleteConnector(connector).thenApply(body -> body.body()).get();
		} catch (InterruptedException | ExecutionException | IOException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
