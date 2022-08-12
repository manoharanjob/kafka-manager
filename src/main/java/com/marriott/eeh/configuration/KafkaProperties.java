package com.marriott.eeh.configuration;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.marriott.eeh.exception.KafkaConfigurationException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

	private final Logger log = LoggerFactory.getLogger(KafkaProperties.class);

	private Map<String, Map<String, String>> dev;

	private Map<String, Map<String, String>> test;

	private Map<String, Map<String, String>> perf;

	public Properties getDevBroker() {
		Properties prop = new Properties();
		prop.putAll(Optional.ofNullable(dev.get("broker"))
				.orElseThrow(() -> new KafkaConfigurationException("Dev environment broker configuration is missing")));
		return prop;
	}

	public Map<String, String> getDevSchema() {
		return Optional.ofNullable(dev.get("schema"))
				.orElseThrow(() -> new KafkaConfigurationException("Dev environment schema configuration is missing"));
	}

	public Map<String, String> getDevConnect() {
		return Optional.ofNullable(dev.get("connect"))
				.orElseThrow(() -> new KafkaConfigurationException("Dev environment connect configuration is missing"));
	}

	public Properties getTestBroker() {
		Properties prop = new Properties();
		prop.putAll(Optional.ofNullable(test.get("broker")).orElseThrow(
				() -> new KafkaConfigurationException("Test environment broker configuration is missing")));
		return prop;
	}

	public Map<String, String> getTestSchema() {
		return Optional.ofNullable(test.get("schema"))
				.orElseThrow(() -> new KafkaConfigurationException("Test environment schema configuration is missing"));
	}

	public Map<String, String> getTestConnect() {
		return Optional.ofNullable(test.get("connect")).orElseThrow(
				() -> new KafkaConfigurationException("Test environment connect configuration is missing"));
	}

	public Properties getPerfBroker() {
		Properties prop = new Properties();
		prop.putAll(Optional.ofNullable(perf.get("broker")).orElseThrow(
				() -> new KafkaConfigurationException("Perf environment broker configuration is missing")));
		return prop;
	}

	public Map<String, String> getPerfSchema() {
		return Optional.ofNullable(perf.get("schema"))
				.orElseThrow(() -> new KafkaConfigurationException("Perf environment schema configuration is missing"));
	}

	public Map<String, String> getPerfConnect() {
		return Optional.ofNullable(perf.get("connect")).orElseThrow(
				() -> new KafkaConfigurationException("Perf environment connect configuration is missing"));
	}
}
