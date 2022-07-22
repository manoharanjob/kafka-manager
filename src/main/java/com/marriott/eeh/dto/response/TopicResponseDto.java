package com.marriott.eeh.dto.response;

import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.common.Uuid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicResponseDto {

	private Uuid topicId;
	private String topicName;
	private int numPartitions;
	private int replicationFactor;
	private Config config;
	private boolean error;
	private String exception;

}
