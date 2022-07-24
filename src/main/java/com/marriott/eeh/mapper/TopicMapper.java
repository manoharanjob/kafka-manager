package com.marriott.eeh.mapper;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.model.Partition;

@Component
public class TopicMapper extends AbstractMapper {

	private final Logger log = LoggerFactory.getLogger(TopicMapper.class);

	public TopicResponseDto convertFromTopicToTopicResponseDto(TopicDescription topic) {
		Collection<TopicPartitionInfo> partitions = ifNullGetEmpty(topic.partitions());
		Collection<AclOperation> operations = ifNullGetEmpty(topic.authorizedOperations());
		return TopicResponseDto.builder()
				.topicId(topic.topicId())
				.topicName(topic.name())
				.partitions(partitions.stream()
						.map(partition -> convertToPartition(partition))
						.collect(Collectors.toList()))
				.operations(operations.stream()
						.map(operation -> operation.name())
						.collect(Collectors.toList()))
				.build();
	}
	
	public Partition convertToPartition(TopicPartitionInfo topicPartitionInfo) {
		Collection<Node> replicas = ifNullGetEmpty(topicPartitionInfo.replicas());
		Collection<Node> isrs = ifNullGetEmpty(topicPartitionInfo.isr());
		return Partition.builder()
				.partition(topicPartitionInfo.partition())
				.leader(topicPartitionInfo.leader() == null ? null : topicPartitionInfo.leader().id())
				.replicas(replicas.stream().map(replica -> replica.id()).collect(Collectors.toList()))
				.isr(isrs.stream().map(isr -> isr.id()).collect(Collectors.toList()))
				.build();
	}

	public TopicResponseDto convertFromConfigToTopicResponseDto(Map.Entry<ConfigResource, Config> entry) {
		return TopicResponseDto.builder()
				.topicName(entry.getKey().name())
				.configs(convertToConfig(entry.getValue()))
				.build();
	}
	
	public Collection<com.marriott.eeh.model.Config> convertToConfig(Config config) {
		if(config == null)
			return null;
		
		return config.entries()
				.stream()
				.map(cf -> com.marriott.eeh.model.Config.builder()
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
