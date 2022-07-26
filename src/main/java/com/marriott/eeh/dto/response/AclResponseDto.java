package com.marriott.eeh.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AclResponseDto {

	private String resourceType;
	private String name;
	private String patternType;
	private String principal;
	private String host;
	private String operation;
	private String permissionType;

}
