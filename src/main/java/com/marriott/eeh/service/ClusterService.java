package com.marriott.eeh.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.client.KafkaClient;
import com.marriott.eeh.dto.response.ClusterResponseDto;
import com.marriott.eeh.exception.KafkaExecutionException;
import com.marriott.eeh.mapper.ClusterMapper;

@Service
public class ClusterService {

	private final Logger log = LoggerFactory.getLogger(ClusterService.class);

	@Autowired
	private KafkaClient kafkaClient;
	@Autowired
	private ClusterMapper clusterMapper;

	public ClusterResponseDto getClusters() throws KafkaExecutionException {
		try {
			DescribeClusterResult clusterResult = kafkaClient.getClusters();
			return ClusterResponseDto.builder()
					.clusterId(clusterResult.clusterId().get())
					.controller(clusterMapper.convertToNode(clusterResult.controller().get()))
					.brokers(clusterMapper.convertToNodes(clusterResult.nodes().get()))
					.operations(clusterMapper.convertToCollectionString(clusterResult.authorizedOperations().get()))
					.build();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
