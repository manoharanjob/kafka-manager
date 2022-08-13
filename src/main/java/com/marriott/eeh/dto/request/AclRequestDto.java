package com.marriott.eeh.dto.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marriott.eeh.validator.constraint.AclOperationType;
import com.marriott.eeh.validator.constraint.AclPatternType;
import com.marriott.eeh.validator.constraint.AclPermissionType;
import com.marriott.eeh.validator.constraint.AclResourceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AclRequestDto {

	@AclResourceType
	private String resourceType;
	@NotBlank
	private String name;
	@AclPatternType
	private String patternType;
	@NotBlank
	private String principal;
	private String host;
	@AclOperationType
	private String operationType;
	@AclPermissionType
	private String permissionType;

}
