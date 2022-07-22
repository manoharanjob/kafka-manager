package com.marriott.eeh.kafka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marriott.eeh.dto.request.SchemaRequestDto;
import com.marriott.eeh.exception.SchemaExecutionException;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

@Service
public class SchemaService {

	private final Logger log = LoggerFactory.getLogger(SchemaService.class);

	@Autowired
	private SchemaRegistryClient schemaRegistryClient;

	public Collection<String> getSchemas() {
		try {
			return schemaRegistryClient.getAllSubjects();
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public Collection<Integer> getAllVersions(String subject) {
		try {
			return schemaRegistryClient.getAllVersions(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public SchemaMetadata getLatestSchema(String subject) {
		try {
			return schemaRegistryClient.getLatestSchemaMetadata(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public String getCompatibility(String subject) {
		try {
			return schemaRegistryClient.getCompatibility(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public int createSchema(SchemaRequestDto schema) {
		try {
			final ParsedSchema parsedSchema = parseSchema(schema.getSchema(), schema.getSchemaType());
			return schemaRegistryClient.register(schema.getSubject(), parsedSchema);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public Collection<Integer> createSchemas(Collection<SchemaRequestDto> schemas) {
		Map<String, ParsedSchema> parsedSchemas = schemas.stream().collect(Collectors.toMap(
				SchemaRequestDto::getSubject, schema -> parseSchema(schema.getSchema(), schema.getSchemaType())));
		return parsedSchemas.entrySet().stream().map(entry -> {
			try {
				return schemaRegistryClient.register(entry.getKey(), entry.getValue());
			} catch (IOException | RestClientException e) {
				return -1;
			}
		}).collect(Collectors.toList());
	}

	public ParsedSchema parseSchema(String schema, String schemaType) {
		return schemaRegistryClient.parseSchema(schema, schemaType, Collections.emptyList()).orElseThrow(() -> {
			return new SchemaExecutionException("", "Schema Parsing failed");
		});
	}

	public String updateCompatibility(String subject, String compatibility) {
		try {
			return schemaRegistryClient.updateCompatibility(subject, compatibility);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

}
