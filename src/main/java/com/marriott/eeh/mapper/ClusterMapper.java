package com.marriott.eeh.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.kafka.common.Node;
import org.apache.kafka.common.acl.AclOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClusterMapper extends AbstractMapper {

	private final Logger log = LoggerFactory.getLogger(ClusterMapper.class);

	public Collection<com.marriott.eeh.model.Node> convertToNodes(Collection<Node> nodes) {
		if(nodes == null)
			return null;
		
		return nodes.stream()
				.map(node -> convertToNode(node))
				.collect(Collectors.toList());
	}
	
	public Collection<String> convertToCollectionString(Collection<AclOperation> operations) {
		if(operations == null)
			return null;
		
		return operations.stream()
				.map(operation -> operation.name())
				.collect(Collectors.toList());
	}
}
