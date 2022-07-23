package com.marriott.eeh.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.client.KafkaClient;
import com.marriott.eeh.exception.KafkaExecutionException;

@Service
public class ConsumerGroupService {

	private final Logger log = LoggerFactory.getLogger(ConsumerGroupService.class);

	@Autowired
	private KafkaClient kafkaClient;

	public Collection<ConsumerGroupListing> getConsumerGroups() throws KafkaExecutionException {
		try {
			return kafkaClient.getConsumerGroups().valid().get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Map<String, ConsumerGroupDescription> getConsumerGroupDetails(String groupId) {
		return getConsumerGroupsDetails(Arrays.asList(groupId));
	}

	public Map<String, ConsumerGroupDescription> getConsumerGroupsDetails(Collection<String> groupIds)
			throws KafkaExecutionException {
		try {
			return kafkaClient.getConsumerGroupsDetails(groupIds).all().get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public boolean deleteConsumerGroup(String groupId) {
		return deleteConsumerGroups(Arrays.asList(groupId));
	}

	public boolean deleteConsumerGroups(Collection<String> groupIds) throws KafkaExecutionException {
		try {
			kafkaClient.deleteConsumerGroups(groupIds).all().get();
			return true;
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
