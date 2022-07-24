package com.marriott.eeh.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.AlterConfigOp.OpType;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.ConfigEntry.ConfigSource;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.marriott.eeh.client.KafkaClient;
import com.marriott.eeh.dto.request.TopicRequestDto;
import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.exception.KafkaExecutionException;
import com.marriott.eeh.mapper.TopicMapper;

@Service
public class TopicService {

	private final Logger log = LoggerFactory.getLogger(TopicService.class);

	@Value("${kafka.topic.internalTopicPrefix:_}")
	private String internalTopicPrefix;

	@Autowired
	private KafkaClient kafkaClient;
	@Autowired
	private TopicMapper topicMapper;

	public Collection<String> getTopics() throws KafkaExecutionException {
		try {
			return kafkaClient.getTopics()
					.names()
					.get()
					.stream()
					.filter(topic -> !topic.startsWith(internalTopicPrefix))
					.collect(Collectors.toList());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<TopicResponseDto> getTopicDetails(String topic) {
		return getTopicsDetails(Arrays.asList(topic));
	}

	public Collection<TopicResponseDto> getTopicsDetails(Collection<String> topics) throws KafkaExecutionException {
		try {
			return kafkaClient.getTopicsDetails(topics)
					.allTopicNames()
					.get()
					.values()
					.stream()
					.map(topic -> topicMapper.convertFromTopicToTopicResponseDto(topic))
					.collect(Collectors.toList());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<TopicResponseDto> getTopicConfigs(String topic) {
		return getTopicsConfigs(Arrays.asList(topic));
	}

	public Collection<TopicResponseDto> getTopicsConfigs(Collection<String> topics) {
		try {
			return kafkaClient.getTopicsConfig(topics.stream()
						.map(topic -> new ConfigResource(Type.TOPIC, topic))
						.collect(Collectors.toList()))
					.all()
					.get()
					.entrySet()
					.stream()
					.map(entry -> topicMapper.convertFromConfigToTopicResponseDto(entry))
					.collect(Collectors.toList());
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

		Collection<NewTopic> list = topics.stream()
				.map(topic -> new NewTopic(topic.getTopicName(), Optional.of(topic.getPartition()), Optional.of(topic.getReplicationFactor())))
				.collect(Collectors.toList());

		CreateTopicsResult createTopicResult = kafkaClient.createTopics(list);

		Collection<TopicResponseDto> result = new ArrayList<>(topics.size());
		topics.forEach(topic -> {
			TopicResponseDto topicResponse = new TopicResponseDto();
			topicResponse.setTopicName(topic.getTopicName());
			try {
				topicResponse.setTopicId(createTopicResult.topicId(topic.getTopicName()).get());
				topicResponse.setPartition(createTopicResult.numPartitions(topic.getTopicName()).get());
				topicResponse.setReplicationFactor(createTopicResult.replicationFactor(topic.getTopicName()).get());
				topicResponse.setConfigs(topicMapper.convertToConfig(createTopicResult.config(topic.getTopicName()).get()));
			} catch (InterruptedException | ExecutionException e) {
				log.error("Exception:{}-{}", topic.getTopicName(), e.getMessage());
				topicResponse.setError(true);
				topicResponse.setException(e.getMessage());
			}
			result.add(topicResponse);
		});
		return result;
	}

	public Collection<TopicResponseDto> updateTopicConfig(TopicRequestDto topic) {
		updateTopicsConfig(Arrays.asList(topic));
		return getTopicConfigs(topic.getTopicName());
	}
	
	public Collection<TopicResponseDto> updateTopicsConfig(Collection<TopicRequestDto> topics) {
		try {
			Map<String, Config> currentConfigs = kafkaClient.getTopicsConfig(topics.stream()
														.map(topic -> new ConfigResource(Type.TOPIC, topic.getTopicName()))
														.collect(Collectors.toList()))
													.all()
													.get()
													.entrySet()
													.stream()
													.collect(Collectors.toMap(entry -> entry.getKey().name(), entry -> entry.getValue()));
			
			Map<ConfigResource, Collection<AlterConfigOp>> configs = topics.stream()
						.collect(Collectors.toMap(topic -> new ConfigResource(Type.TOPIC, topic.getTopicName()),
								topic -> filterNewOrUpdateConfig(topic.getConfig(), currentConfigs.get(topic.getTopicName()))));
		
		
			kafkaClient.updateTopicsConfig(configs).all().get();
			return getTopicsConfigs(topics.stream().map(topic -> topic.getTopicName()).collect(Collectors.toList()));
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	private Collection<AlterConfigOp> filterNewOrUpdateConfig(Map<String, Object> config, Config currentConfig) {
		return config.entrySet()
				.stream()
				.filter(entry -> {
					ConfigEntry currentConfigEntry = currentConfig.get(entry.getKey());
					return currentConfigEntry == null || (!currentConfigEntry.value().equals(entry.getValue())
							&& currentConfigEntry.source().equals(ConfigSource.DYNAMIC_TOPIC_CONFIG));
					})
				.map(entry -> new AlterConfigOp(new ConfigEntry(entry.getKey(), entry.getValue().toString()), OpType.SET))
				.collect(Collectors.toList());
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
