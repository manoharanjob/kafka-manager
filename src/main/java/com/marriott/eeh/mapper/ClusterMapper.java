package com.marriott.eeh.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.kafka.common.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marriott.eeh.dto.response.ClusterResponseDto;

@Component
public class ClusterMapper {

	private final Logger log = LoggerFactory.getLogger(ClusterMapper.class);

	public ClusterResponseDto.Node convertToNode(Node node) {
		return ClusterResponseDto.Node.builder()
				.id(node.id())
				.idString(node.idString())
				.host(node.host())
				.port(node.port())
				.rack(node.rack())
				.build();
	}
	
	public Collection<ClusterResponseDto.Node> convertToNodes(Collection<Node> nodes) {
		return nodes.stream()
				.map(node -> convertToNode(node))
				.collect(Collectors.toList());
	}
	
}
