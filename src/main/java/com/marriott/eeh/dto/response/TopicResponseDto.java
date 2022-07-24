package com.marriott.eeh.dto.response;

import java.util.Collection;

import com.marriott.eeh.model.Config;
import com.marriott.eeh.model.Partition;

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
public class TopicResponseDto {

	private String topicId;
	private String topicName;
	private int partition;
	private int replicationFactor;
	private Collection<Partition> partitions;
	private Collection<String> operations;
	private Collection<Config> configs;
	private boolean error;
	private String exception;
	
}
