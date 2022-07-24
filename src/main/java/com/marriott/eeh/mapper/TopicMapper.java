package com.marriott.eeh.mapper;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.dto.response.TopicResponseDto.PartitionInfo;

@Component
public class TopicMapper {

	private final Logger log = LoggerFactory.getLogger(TopicMapper.class);

	public TopicResponseDto convertFromTopicToTopicResponseDto(Map.Entry<String, TopicDescription> entry) {
		return TopicResponseDto.builder()
				.topicId(entry.getValue().topicId())
				.topicName(entry.getKey())
				.partitions(entry.getValue()
						.partitions()
						.stream()
						.map(partition -> convertToPartitionInfo(partition))
						.collect(Collectors.toList()))
				.operations(entry.getValue()
						.authorizedOperations()
						.stream()
						.map(operation -> operation.name())
						.collect(Collectors.toList()))
				.build();
	}
	
	public PartitionInfo convertToPartitionInfo(TopicPartitionInfo topicPartitionInfo) {
		return PartitionInfo.builder()
				.partition(topicPartitionInfo.partition())
				.leader(topicPartitionInfo.leader().id())
				.replicas(topicPartitionInfo.replicas().stream().map(replica -> replica.id()).collect(Collectors.toList()))
				.isr(topicPartitionInfo.isr().stream().map(issr -> issr.id()).collect(Collectors.toList()))
				.build();
	}

	public TopicResponseDto convertFromConfigToTopicResponseDto(Map.Entry<ConfigResource, Config> entry) {
		return TopicResponseDto.builder()
				.topicName(entry.getKey().name())
				.configs(convertToConfig(entry.getValue()))
				.build();
	}
	
	public Collection<TopicResponseDto.Config> convertToConfig(Config config) {
		return config.entries()
				.stream()
				.map(cf -> TopicResponseDto.Config.builder()
						.name(cf.name())
						.value(cf.value())
						.source(cf.source())
						.isSensitive(cf.isSensitive())
						.isReadOnly(cf.isSensitive())
						.type(cf.type())
						.build())
				.collect(Collectors.toList());
	}
	
}
