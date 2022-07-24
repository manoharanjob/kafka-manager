package com.marriott.eeh.service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marriott.eeh.client.SchemaClient;
import com.marriott.eeh.dto.request.SchemaRequestDto;
import com.marriott.eeh.exception.SchemaExecutionException;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

@Service
public class SchemaService {

	private final Logger log = LoggerFactory.getLogger(SchemaService.class);

	@Autowired
	private SchemaClient schemaClient;
	@Autowired
	private ObjectMapper objectMapper;

	public Collection<String> getSchemas() {
		try {
			return schemaClient.getSchemas();
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public Collection<Integer> getAllVersions(String subject) {
		try {
			return schemaClient.getAllVersions(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public SchemaMetadata getLatestSchema(String subject) {
		try {
			return schemaClient.getLatestSchema(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public String getCompatibility(String subject) {
		try {
			return schemaClient.getCompatibility(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public int createSchema(SchemaRequestDto schema) {
		try {
			final ParsedSchema parsedSchema = parseSchema(schema.getSchema(), schema.getSchemaType());
			return schemaClient.createSchema(schema.getSubject(), parsedSchema);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public Collection<Integer> createSchemas(Collection<SchemaRequestDto> schemas) {
		Map<String, ParsedSchema> parsedSchemas = schemas.stream().collect(Collectors.toMap(
				SchemaRequestDto::getSubject, schema -> parseSchema(schema.getSchema(), schema.getSchemaType())));
		return schemaClient.createSchemas(parsedSchemas);
	}

	public Integer deleteSchema(String subject, String version) {
		try {
			return schemaClient.deleteSchema(subject, version);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public Collection<Integer> deleteSchemas(String subject) {
		try {
			return schemaClient.deleteSchemas(subject);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

	public ParsedSchema parseSchema(String schema, String schemaType) {
		return schemaClient.parseSchema(schema, schemaType);
	}

	public String updateCompatibility(String subject, String compatibility) {
		try {
			return schemaClient.updateCompatibility(subject, compatibility);
		} catch (Exception e) {
			throw new SchemaExecutionException("ERR_002", e.getMessage());
		}
	}

}
