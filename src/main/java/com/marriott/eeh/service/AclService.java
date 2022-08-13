package com.marriott.eeh.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
import com.marriott.eeh.dto.response.AclResponseDto;
import com.marriott.eeh.exception.KafkaExecutionException;
import com.marriott.eeh.mapper.AclMapper;

@Service
public class AclService {

	private final Logger log = LoggerFactory.getLogger(AclService.class);

	@Autowired
	private KafkaClient kafkaClient;
	@Autowired
	private AclMapper aclMapper;

	public Collection<AclResponseDto> getAcls() throws KafkaExecutionException {
		try {
			return kafkaClient.getAcls()
					.values()
					.get()
					.stream()
					.map(aclBinding -> aclMapper.convertFromAclBindingToAclResponseDto(aclBinding))
					.collect(Collectors.toList());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<AclResponseDto> getAclsByFilter(AclRequestDto acl) throws KafkaExecutionException {
		try {
			return kafkaClient.getAcl(AclBindingFilterBuilder.builder()
						.addResourceFilter(ResourceType.fromString(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
						.addControlFilter(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperationType()), AclPermissionType.fromString(acl.getPermissionType()))
						.build())
					.values()
					.get()
					.stream()
					.map(aclBinding -> aclMapper.convertFromAclBindingToAclResponseDto(aclBinding))
					.collect(Collectors.toList());
		} catch (InterruptedException | ExecutionException e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<AclResponseDto> createAcl(AclRequestDto acl) throws KafkaExecutionException {
		return createAcls(Arrays.asList(acl));
	}

	public Collection<AclResponseDto> createAcls(Collection<AclRequestDto> acls) {
		try {
			return kafkaClient.createAcls(acls.stream()
						.map(acl -> AclBindingBuilder.builder()
							.addResource(ResourceType.fromString(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
							.addControl(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperationType()), AclPermissionType.fromString(acl.getPermissionType()))
							.build())
						.collect(Collectors.toList()))
					.values()
					.keySet()
					.stream()
					.map(aclBinding -> aclMapper.convertFromAclBindingToAclResponseDto(aclBinding))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

	public Collection<AclResponseDto> deleteAcl(AclRequestDto acl) throws KafkaExecutionException {
		return deleteAcls(Arrays.asList(acl));
	}

	public Collection<AclResponseDto> deleteAcls(Collection<AclRequestDto> acls) {
		try {
			return kafkaClient.deleteAcls(acls.stream()
						.map(acl -> AclBindingFilterBuilder.builder()
							.addResourceFilter(ResourceType.valueOf(acl.getResourceType()), acl.getName(), PatternType.fromString(acl.getPatternType()))
							.addControlFilter(acl.getPrincipal(), acl.getHost(), AclOperation.fromString(acl.getOperationType()), AclPermissionType.fromString(acl.getPermissionType()))
							.build())
						.collect(Collectors.toList()))
					.all()
					.get()
					.stream()
					.map(aclBinding -> aclMapper.convertFromAclBindingToAclResponseDto(aclBinding))
					.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Exception:{}", e.getMessage());
			throw new KafkaExecutionException("ERR_001", e.getMessage());
		}
	}

}
