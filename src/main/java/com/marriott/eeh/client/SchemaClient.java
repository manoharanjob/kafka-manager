package com.marriott.eeh.client;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marriott.eeh.exception.SchemaExecutionException;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

@Component
public class SchemaClient {

	private final Logger log = LoggerFactory.getLogger(SchemaClient.class);

//	@Autowired
	private SchemaRegistryClient schemaRegistryClient;

	public Collection<String> getSchemas() throws IOException, RestClientException {
		return schemaRegistryClient.getAllSubjects();
	}

	public Collection<Integer> getAllVersions(String subject) throws IOException, RestClientException {
		return schemaRegistryClient.getAllVersions(subject);
	}

	public SchemaMetadata getLatestSchema(String subject) throws IOException, RestClientException {
		return schemaRegistryClient.getLatestSchemaMetadata(subject);
	}

	public String getCompatibility(String subject) throws IOException, RestClientException {
		return schemaRegistryClient.getCompatibility(subject);
	}

	public int createSchema(String subject, ParsedSchema parsedSchema) throws IOException, RestClientException {
		return schemaRegistryClient.register(subject, parsedSchema);
	}

	public Collection<Integer> createSchemas(Map<String, ParsedSchema> schemas) {
		return schemas.entrySet().stream().map(entry -> {
			try {
				return schemaRegistryClient.register(entry.getKey(), entry.getValue());
			} catch (IOException | RestClientException e) {
				return -1;
			}
		}).collect(Collectors.toList());
	}

	public Integer deleteSchema(String subject, String version) throws IOException, RestClientException {
		return schemaRegistryClient.deleteSchemaVersion(subject, version);
	}

	public Collection<Integer> deleteSchemas(String subject) throws IOException, RestClientException {
		return schemaRegistryClient.deleteSubject(subject);
	}

	public ParsedSchema parseSchema(String schema, String schemaType) {
		return schemaRegistryClient.parseSchema(schemaType, schema, Collections.emptyList()).orElseThrow(() -> {
			return new SchemaExecutionException("", "Schema Parsing failed");
		});
	}

	public String updateCompatibility(String subject, String compatibility) throws IOException, RestClientException {
		return schemaRegistryClient.updateCompatibility(subject, compatibility);
	}

}
