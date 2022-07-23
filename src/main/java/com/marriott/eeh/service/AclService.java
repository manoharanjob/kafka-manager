package com.marriott.eeh.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.PatternType;
import org.apache.kafka.common.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.builder.AclBindingBuilder;
import com.marriott.eeh.builder.AclBindingFilterBuilder;
import com.marriott.eeh.client.KafkaClient;
import com.marriott.eeh.dto.request.AclRequestDto;
import com.marriott.eeh.exception.KafkaExecutionException;

@Service
public class AclService {

	private final Logger log = LoggerFactory.getLogger(AclService.class);

	@Autowired
	private KafkaClient kafkaClient;

	public Collection<AclBinding> getAcls() throws KafkaExecutionException {
		try {
			return kafkaClient.getAcls().values().get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<AclBinding> getAclsByFilter(AclRequestDto acl) throws KafkaExecutionException {
		try {
			return kafkaClient.getAcl(AclBindingFilterBuilder.builder()
						.addResourceFilter(ResourceType.valueOf(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
						.addControlFilter(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperation()), AclPermissionType.fromString(acl.getPermissionType()))
						.build())
					.values().get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<AclBinding> createAcl(AclRequestDto acl) throws KafkaExecutionException {
		return applyAcls(Arrays.asList(AclBindingBuilder.builder()
					.addResource(ResourceType.valueOf(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
					.addControl(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperation()), AclPermissionType.fromString(acl.getPermissionType()))
					.build()));
	}

	public Collection<AclBinding> createAcls(Collection<AclRequestDto> acls) {
		return applyAcls(acls.stream()
					.map(acl -> AclBindingBuilder.builder()
							.addResource(ResourceType.valueOf(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
							.addControl(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperation()), AclPermissionType.fromString(acl.getPermissionType()))
							.build())
					.collect(Collectors.toList()));
	}

	private Collection<AclBinding> applyAcls(Collection<AclBinding> acls) {
		try {
			return kafkaClient.createAcls(acls).values().keySet();
		} catch (Exception e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<AclBinding> deleteAcl(AclRequestDto acl) throws KafkaExecutionException {
		return removeAcls(Arrays.asList(AclBindingFilterBuilder.builder()
					.addResourceFilter(ResourceType.valueOf(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
					.addControlFilter(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperation()), AclPermissionType.fromString(acl.getPermissionType()))
					.build()));
	}

	public Collection<AclBinding> deleteAcls(Collection<AclRequestDto> acls) {
		return removeAcls(acls.stream()
					.map(acl -> AclBindingFilterBuilder.builder()
							.addResourceFilter(ResourceType.valueOf(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
							.addControlFilter(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperation()), AclPermissionType.fromString(acl.getPermissionType()))
							.build())
					.collect(Collectors.toList()));
	}

	private Collection<AclBinding> removeAcls(Collection<AclBindingFilter> acls) {
		try {
			return kafkaClient.deleteAcls(acls).all().get();
		} catch (Exception e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}
}
