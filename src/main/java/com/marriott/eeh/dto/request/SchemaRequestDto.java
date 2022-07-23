package com.marriott.eeh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchemaRequestDto {

	private String subject;
	private String schema;
	private String schemaType;

}
