package com.marriott.eeh.dto.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ClusterResponseDto {

	private String clusterId;
	private Node controller;
	private Collection<Node> brokers;
	private Collection<String> operations;

}
