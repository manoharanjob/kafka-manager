package com.marriott.eeh.dto.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicResponseDto {

	private String topicId;
	private String topicName;
	private Integer partition;
	private Integer replicationFactor;
	private Collection<Partition> partitions;
	private Collection<String> operations;
	private Collection<Config> configs;
	private Boolean error;
	private String exception;
	
}
