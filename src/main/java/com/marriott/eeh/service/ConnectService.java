package com.marriott.eeh.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.client.ConnectClient;
import com.marriott.eeh.exception.KafkaExecutionException;

@Service
public class ConnectService {

	private final Logger log = LoggerFactory.getLogger(ConnectService.class);

	@Autowired
	private ConnectClient connectClient;

	public String getConnectors() throws KafkaExecutionException {
		try {
			return connectClient.getConnectors().body();
		} catch (InterruptedException | IOException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
