package com.marriott.eeh.dto.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.marriott.eeh.model.Member;
import com.marriott.eeh.model.Node;

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
public class ConsumerGroupResponseDto {

	private String groupId;
	private String groupName;
	private Boolean isSimpleConsumerGroup;
	private String state;
	private Collection<Member> members;
	private String partitionAssignor;
	private Node coordinator;
	private Collection<String> operations;

}
