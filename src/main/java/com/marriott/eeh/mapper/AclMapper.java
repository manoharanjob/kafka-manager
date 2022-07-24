package com.marriott.eeh.mapper;

import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.resource.ResourcePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.marriott.eeh.dto.response.AclResponseDto;

@Component
public class AclMapper extends AbstractMapper {

	private final Logger log = LoggerFactory.getLogger(AclMapper.class);

	public AclResponseDto convertFromAclBindingToAclResponseDto(AclBinding aclBinding) {
		ResourcePattern pattern = aclBinding.pattern();
		AccessControlEntry entry = aclBinding.entry();
		return AclResponseDto.builder()
				.resourceType(pattern.resourceType().name())
				.name(pattern.name())
				.patternType(pattern.patternType().name())
				.principal(entry.principal())
				.host(entry.host())
				.operation(entry.operation().name())
				.permissionType(entry.permissionType().name())
				.build();
	}
	
}
