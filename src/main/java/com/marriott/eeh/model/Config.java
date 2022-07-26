package com.marriott.eeh.model;

import org.apache.kafka.clients.admin.ConfigEntry.ConfigSource;
import org.apache.kafka.clients.admin.ConfigEntry.ConfigType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Config {

	private String name;
	private String value;
	private ConfigSource source;
	private Boolean isSensitive;
	private Boolean isReadOnly;
	private ConfigType type;

}
