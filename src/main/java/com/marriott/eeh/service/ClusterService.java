package com.marriott.eeh.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.exception.KafkaExecutionException;

@Service
public class ClusterService {

	private final Logger log = LoggerFactory.getLogger(ClusterService.class);

	@Autowired
	private KafkaService kafkaService;

	public Map<String, Object> getClusters() throws KafkaExecutionException {
		try {
			DescribeClusterResult clusterResult = kafkaService.getClusters();
			Map<String, Object> map = new HashMap<>();
			map.put("clusterId", clusterResult.clusterId().get());
			map.put("controller", clusterResult.controller().get());
			map.put("nodes", clusterResult.nodes().get());
			map.put("authorizedOperations", clusterResult.authorizedOperations().get());
			return map;
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
