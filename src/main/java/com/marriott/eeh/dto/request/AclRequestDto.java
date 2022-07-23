package com.marriott.eeh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AclRequestDto {

	private String resourceType;
	private String name;
	private String patternType;
	private String principal;
	private String host;
	private String operation;
	private String permissionType;

}
