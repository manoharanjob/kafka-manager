package com.marriott.eeh.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.client.KafkaClient;
import com.marriott.eeh.dto.response.ConsumerGroupResponseDto;
import com.marriott.eeh.exception.KafkaExecutionException;
import com.marriott.eeh.mapper.ConsumerGroupMapper;

@Service
public class ConsumerGroupService {

	private final Logger log = LoggerFactory.getLogger(ConsumerGroupService.class);

	@Autowired
	private KafkaClient kafkaClient;
	@Autowired
	private ConsumerGroupMapper consumerGroupMapper;

	public Collection<ConsumerGroupResponseDto> getConsumerGroups() throws KafkaExecutionException {
		try {
			return kafkaClient.getConsumerGroups()
					.valid()
					.get()
					.stream()
					.map(group -> consumerGroupMapper.convertToConsumerGroupResponseDto(group))
					.collect(Collectors.toList());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<ConsumerGroupResponseDto> getConsumerGroupDetails(String groupId) {
		return getConsumerGroupsDetails(Arrays.asList(groupId));
	}

	public Collection<ConsumerGroupResponseDto> getConsumerGroupsDetails(Collection<String> groupIds)
			throws KafkaExecutionException {
		try {
			return kafkaClient.getConsumerGroupsDetails(groupIds)
					.all()
					.get()
					.entrySet()
					.stream()
					.map(entry -> consumerGroupMapper.convertToConsumerGroupResponseDto(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
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
