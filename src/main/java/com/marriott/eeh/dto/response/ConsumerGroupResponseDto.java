package com.marriott.eeh.dto.response;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerGroupResponseDto {

	private String groupId;
	private String groupName;
	private boolean isSimpleConsumerGroup;
	private String state;
	private Collection<Member> members;
	private String partitionAssignor;
	private Node coordinator;
	private Collection<String> operations;

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Member {
		private String memberId;
		private String groupInstanceId;
		private String clientId;
		private String host;
		private Collection<Topic> topics;
	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Topic {
		private int partition;
		private String topic;
	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Node {
		private int id;
		private String idString;
		private String host;
		private int port;
		private String rack;
	}

}
