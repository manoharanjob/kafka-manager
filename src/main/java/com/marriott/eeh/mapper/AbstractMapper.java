package com.marriott.eeh.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marriott.eeh.model.Node;

public class AbstractMapper {

	private final Logger log = LoggerFactory.getLogger(AbstractMapper.class);

	public Node convertToNode(org.apache.kafka.common.Node node) {
		if(node == null)
			return null;
		
		return com.marriott.eeh.model.Node.builder()
				.id(node.id())
				.idString(node.idString())
				.host(node.host())
				.port(node.port())
				.rack(node.rack())
				.build();
	}
	
	public <T> Collection<T> ifNullGetEmpty(Collection<T> collection) {
		return collection == null ? new ArrayList<>() : collection;
	}

}
