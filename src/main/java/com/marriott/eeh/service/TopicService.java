package com.marriott.eeh.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.marriott.eeh.client.KafkaClient;
import com.marriott.eeh.dto.request.TopicRequestDto;
import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.exception.KafkaExecutionException;

@Service
public class TopicService {

	private final Logger log = LoggerFactory.getLogger(TopicService.class);

	@Value("${kafka.topic.internalTopicPrefix:_}")
	private String internalTopicPrefix;

	@Autowired
	private KafkaClient kafkaClient;

	public Collection<String> getTopics() throws KafkaExecutionException {
		try {
			return kafkaClient.getTopics().names().get().stream()
					.filter(topic -> !topic.startsWith(internalTopicPrefix)).collect(Collectors.toList());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Map<String, TopicDescription> getTopicDetails(String topic) {
		return getTopicsDetails(Arrays.asList(topic));
	}

	public Map<String, TopicDescription> getTopicsDetails(Collection<String> topics) throws KafkaExecutionException {
		try {
			return kafkaClient.getTopicsDetails(topics).allTopicNames().get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<TopicResponseDto> createTopic(TopicRequestDto topic) {
		return createTopics(Arrays.asList(topic));
	}

	public Collection<TopicResponseDto> createTopics(Collection<TopicRequestDto> topics)
			throws KafkaExecutionException {

		Collection<NewTopic> list = topics.stream().map(topic -> new NewTopic(topic.getTopicName(),
				Optional.of(topic.getPartition()), Optional.of(topic.getReplicationFactor())))
				.collect(Collectors.toList());

		CreateTopicsResult createTopicResult = kafkaClient.createTopics(list);

		Collection<TopicResponseDto> result = new ArrayList<>(topics.size());
		topics.forEach(topic -> {
			TopicResponseDto topicResponse = new TopicResponseDto();
			topicResponse.setTopicName(topic.getTopicName());
			try {
				topicResponse.setTopicId(createTopicResult.topicId(topic.getTopicName()).get());
				topicResponse.setNumPartitions(createTopicResult.numPartitions(topic.getTopicName()).get());
				topicResponse.setReplicationFactor(createTopicResult.replicationFactor(topic.getTopicName()).get());
				topicResponse.setConfig(createTopicResult.config(topic.getTopicName()).get());
			} catch (InterruptedException | ExecutionException e) {
				log.error("Exception:{}-{}", topic.getTopicName(), e.getMessage());
				topicResponse.setError(true);
				topicResponse.setException(e.getMessage());
			}
			result.add(topicResponse);
		});
		return result;
	}

	public boolean deleteTopic(String topic) {
		return deleteTopics(Arrays.asList(topic));
	}

	public boolean deleteTopics(Collection<String> topics) throws KafkaExecutionException {
		try {
			kafkaClient.deleteTopics(topics).all().get();
			return true;
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
