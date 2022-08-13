package com.marriott.eeh.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourceType;
import org.springframework.stereotype.Component;

@Component
public class EnumHelper {

	public String getNames(String name) {
		switch (name) {
		case "AclResourceType":
			return String.join(", ", List.of(ResourceType.values()).stream().map(enm -> enm.name())
					.collect(Collectors.toList()).toArray(new String[0]));
		case "AclPatternType":
			return String.join(", ", List.of(PatternType.values()).stream().map(enm -> enm.name())
					.collect(Collectors.toList()).toArray(new String[0]));
		case "AclOperationType":
			return String.join(", ", List.of(AclOperation.values()).stream().map(enm -> enm.name())
					.collect(Collectors.toList()).toArray(new String[0]));
		case "AclPermissionType":
			return String.join(", ", List.of(AclPermissionType.values()).stream().map(enm -> enm.name())
					.collect(Collectors.toList()).toArray(new String[0]));
		default:
			break;
		}
		return null;
	}
}
