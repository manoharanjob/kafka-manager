package com.marriott.eeh.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.MemberAssignment;
import org.apache.kafka.clients.admin.MemberDescription;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.acl.AclOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marriott.eeh.dto.response.ConsumerGroupResponseDto;
import com.marriott.eeh.model.Member;
import com.marriott.eeh.model.Topic;

@Component
public class ConsumerGroupMapper extends AbstractMapper {

	private final Logger log = LoggerFactory.getLogger(ConsumerGroupMapper.class);

	public ConsumerGroupResponseDto convertToConsumerGroupResponseDto(ConsumerGroupListing group) {
		return ConsumerGroupResponseDto.builder()
				.groupId(group.groupId())
				.isSimpleConsumerGroup(group.isSimpleConsumerGroup())
				.state(group.state().isPresent() ? group.state().get().name() : null)
				.build();
	}
	
	public ConsumerGroupResponseDto convertToConsumerGroupResponseDto(String groupName, ConsumerGroupDescription group) {
		Collection<MemberDescription> members = ifNullGetEmpty(group.members());
		Collection<AclOperation> operations = ifNullGetEmpty(group.authorizedOperations());
		return ConsumerGroupResponseDto.builder()
				.groupId(group.groupId())
				.groupName(groupName)
				.isSimpleConsumerGroup(group.isSimpleConsumerGroup())
				.state(group.state().name())
				.partitionAssignor(group.partitionAssignor())
				.coordinator(convertToNode(group.coordinator()))
				.members(members.stream()
						.map(member -> convertToMember(member))
						.collect(Collectors.toList()))
				.operations(operations.stream()
						.map(operation -> operation.name())
						.collect(Collectors.toList()))
				.build();
	}
	
	public Member convertToMember(MemberDescription member) {
		MemberAssignment assignment = member.assignment();
		Collection<TopicPartition> partitions = ifNullGetEmpty(assignment.topicPartitions());
		return Member.builder()
				.memberId(member.consumerId())
				.groupInstanceId(member.groupInstanceId().isPresent() ? member.groupInstanceId().get() : null)
				.clientId(member.clientId())
				.host(member.host())
				.topics(partitions.stream()
						.map(topic -> convertToTopic(topic))
						.collect(Collectors.toList()))
				.build();
	}
	
	public Topic convertToTopic(TopicPartition topic) {
		return Topic.builder()
				.topicName(topic.topic())
				.partition(topic.partition())
				.build();
	}
	
}
