package com.marriott.eeh.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicRequestDto {

	private String topicName;
	private Integer partition;
	private Short replicationFactor;

}
