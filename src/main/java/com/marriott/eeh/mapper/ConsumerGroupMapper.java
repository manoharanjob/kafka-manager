package com.marriott.eeh.mapper;

import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.MemberDescription;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marriott.eeh.dto.response.ConsumerGroupResponseDto;

@Component
public class ConsumerGroupMapper {

	private final Logger log = LoggerFactory.getLogger(ConsumerGroupMapper.class);

	public ConsumerGroupResponseDto convertToConsumerGroupResponseDto(ConsumerGroupListing group) {
		return ConsumerGroupResponseDto.builder()
				.groupId(group.groupId())
				.isSimpleConsumerGroup(group.isSimpleConsumerGroup())
				.state(group.state().isPresent() ? group.state().get().name() : null)
				.build();
	}
	
	public ConsumerGroupResponseDto convertToConsumerGroupResponseDto(String groupName, ConsumerGroupDescription group) {
		return ConsumerGroupResponseDto.builder()
				.groupId(group.groupId())
				.groupName(groupName)
				.isSimpleConsumerGroup(group.isSimpleConsumerGroup())
				.state(group.state().name())
				.partitionAssignor(group.partitionAssignor())
				.coordinator(convertToNode(group.coordinator()))
				.members(group.members()
						.stream()
						.map(member -> convertToMember(member))
						.collect(Collectors.toList()))
				.operations(group.authorizedOperations()
						.stream()
						.map(operation -> operation.name())
						.collect(Collectors.toList()))
				.build();
	}
	
	public ConsumerGroupResponseDto.Member convertToMember(MemberDescription member) {
		return ConsumerGroupResponseDto.Member.builder()
				.memberId(member.consumerId())
				.groupInstanceId(member.groupInstanceId().isPresent() ? member.groupInstanceId().get() : null)
				.clientId(member.clientId())
				.host(member.host())
				.topics(member.assignment()
						.topicPartitions()
						.stream()
						.map(topic -> convertToTopic(topic))
						.collect(Collectors.toList()))
				.build();
	}
	
	public ConsumerGroupResponseDto.Topic convertToTopic(TopicPartition topic) {
		return ConsumerGroupResponseDto.Topic.builder()
				.topic(topic.topic())
				.partition(topic.partition())
				.build();
	}
	
	public ConsumerGroupResponseDto.Node convertToNode(Node node) {
		return ConsumerGroupResponseDto.Node.builder()
				.id(node.id())
				.idString(node.idString())
				.host(node.host())
				.port(node.port())
				.rack(node.rack())
				.build();
	}
	
}
