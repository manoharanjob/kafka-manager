package com.marriott.eeh.dto.response;

import java.util.Collection;

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
public class ClusterResponseDto {

	private String clusterId;
	private Node controller;
	private Collection<Node> brokers;
	private Collection<String> operations;

}
