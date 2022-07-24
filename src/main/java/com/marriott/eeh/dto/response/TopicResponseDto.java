package com.marriott.eeh.dto.response;

import java.util.Collection;

import org.apache.kafka.clients.admin.ConfigEntry.ConfigSource;
import org.apache.kafka.clients.admin.ConfigEntry.ConfigType;
import org.apache.kafka.common.Uuid;

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
public class TopicResponseDto {

	private Uuid topicId;
	private String topicName;
	private int partition;
	private int replicationFactor;
	private Collection<PartitionInfo> partitions;
	private Collection<String> operations;
	private Collection<Config> configs;
	private boolean error;
	private String exception;

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PartitionInfo {
		private int partition;
		private int leader;
		private Collection<Integer> replicas;
		private Collection<Integer> isr;
	}
	
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Config {
		private String name;
	    private String value;
	    private ConfigSource source;
	    private boolean isSensitive;
	    private boolean isReadOnly;
	    private ConfigType type;
	}
	
}
