package com.marriott.eeh.dto.request;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@NotBlank
	private String topicName;
	@NotNull
	private Integer partition;
	@NotNull
	private Short replicationFactor;
	private Map<String, Object> config;

}
