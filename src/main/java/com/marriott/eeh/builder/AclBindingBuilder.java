package com.marriott.eeh.builder;

import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePattern;
import org.apache.kafka.common.resource.ResourceType;

public class AclBindingBuilder {

	private ResourcePattern resource;
	private AccessControlEntry entry;

	private AclBindingBuilder() {
	}

	public static AclBindingBuilder builder() {
		return new AclBindingBuilder();
	}

	public AclBindingBuilder addResource(ResourceType resourceType, String name, PatternType patternType) {
		resource = new ResourcePattern(resourceType, name, patternType);
		return this;
	}

	public AclBindingBuilder addControl(String principal, String host, AclOperation op,
			AclPermissionType permissionType) {
		entry = new AccessControlEntry(principal, host, op, permissionType);
		return this;
	}

	public AclBinding build() {
		return new AclBinding(resource, entry);
	}

}
