package com.marriott.eeh.builder;

import org.apache.kafka.common.acl.AccessControlEntryFilter;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourcePatternFilter;
import org.apache.kafka.common.resource.ResourceType;

public class AclBindingFilterBuilder {

	private ResourcePatternFilter resourceFilter;
	private AccessControlEntryFilter entryFilter;

	private AclBindingFilterBuilder() {
	}

	public static AclBindingFilterBuilder builder() {
		return new AclBindingFilterBuilder();
	}

	public AclBindingFilterBuilder addResourceFilter(ResourceType resourceType, String name, PatternType patternType) {
		resourceFilter = new ResourcePatternFilter(resourceType, name, patternType);
		return this;
	}

	public AclBindingFilterBuilder addControlFilter(String principal, String host, AclOperation op,
			AclPermissionType permissionType) {
		entryFilter = new AccessControlEntryFilter(principal, host, op, permissionType);
		return this;
	}

	public AclBindingFilter build() {
		return new AclBindingFilter(resourceFilter, entryFilter);
	}

}
