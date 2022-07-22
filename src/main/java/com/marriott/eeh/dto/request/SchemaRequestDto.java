package com.marriott.eeh.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchemaRequestDto {

	private String subject;
	private String schema;
	private String schemaType;

}
