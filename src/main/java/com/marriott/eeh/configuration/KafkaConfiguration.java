package com.marriott.eeh.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import io.confluent.kafka.schemaregistry.SchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.schemaregistry.json.JsonSchemaProvider;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;

@Configuration
public class KafkaConfiguration {

	private final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

	@Value("${kafka.file.path}")
	private String kafkaFilePath;

	@Autowired
	private Properties kafkaProperties;

	@Bean
	public Properties kafkaProperties() {
		Properties properties = new Properties();
		InputStream inStream = null;
		try {
			File file = ResourceUtils.getFile("classpath:" + kafkaFilePath);
			inStream = new FileInputStream(file);
			properties.load(inStream);
		} catch (FileNotFoundException e) {
			log.error("Kafka config file not found - {}", kafkaFilePath);
		} catch (IOException e) {
			log.error("Kafka config file read exception - {}", e.getMessage());
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					log.error("Kafka config file stream close exception - {}", e.getMessage());
				}
			}
		}
		return properties;
	}

	@Bean
	public AdminClient adminClient() {
		return AdminClient.create(kafkaProperties);
	}

	@Bean
	public SchemaRegistryClient schemaRegistryClient() {
		Map map = kafkaProperties.entrySet()
				.stream()
				.filter(entry -> entry.getKey().toString().contains("schema") && !entry.getKey().toString().contains("url"))
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
		RestService restService = new RestService(kafkaProperties.getProperty("schema.registry.url"));
		List<SchemaProvider> providers = Arrays.asList(new AvroSchemaProvider(), new JsonSchemaProvider(),
				new ProtobufSchemaProvider());
		SchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(restService, 10, providers, map,
				null);
		return schemaRegistryClient;
	}
}