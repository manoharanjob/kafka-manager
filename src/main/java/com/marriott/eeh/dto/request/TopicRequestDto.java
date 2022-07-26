package com.marriott.eeh.dto.request;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicRequestDto {

	private String topicName;
	private Integer partition;
	private Short replicationFactor;
	private Map<String, Object> config;

}
