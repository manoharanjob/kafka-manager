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

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import io.confluent.kafka.schemaregistry.SchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.schemaregistry.json.JsonSchemaProvider;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;

@Configuration
@PropertySource("classpath:${kafka.file.path}")
@ConfigurationProperties(prefix = "schema.registry")
public class KafkaConfiguration {

	@Value("${kafka.file.path}")
	private String kafkaFilePath;

	@Value("${schema.registry.url}")
	private String schemaRegistryUrl;

	@Autowired
	private Environment env;

	private Map<String, String> ssl;

	@Bean
	public AdminClient adminClient() throws FileNotFoundException {
		Properties props = new Properties();
		File file = ResourceUtils.getFile("classpath:" + kafkaFilePath);
		try (InputStream inStream = new FileInputStream(file)) {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AdminClient.create(props);
	}

	@Bean
	public SchemaRegistryClient schemaRegistryClient() {
		RestService restService = new RestService(schemaRegistryUrl);
		List<SchemaProvider> providers = Arrays.asList(new AvroSchemaProvider(), new JsonSchemaProvider(),
				new ProtobufSchemaProvider());
		SchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(restService, 10, providers, ssl,
				null);
		return schemaRegistryClient;
	}
}