package com.marriott.eeh.model;

import org.apache.kafka.clients.admin.ConfigEntry.ConfigSource;
import org.apache.kafka.clients.admin.ConfigEntry.ConfigType;

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
public class Config {

	private String name;
	private String value;
	private ConfigSource source;
	private boolean isSensitive;
	private boolean isReadOnly;
	private ConfigType type;

}
