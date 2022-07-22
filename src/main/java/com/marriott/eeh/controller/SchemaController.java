package com.marriott.eeh.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.dto.request.SchemaRequestDto;
import com.marriott.eeh.service.SchemaService;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;

@RestController
public class SchemaController {

	private final Logger log = LoggerFactory.getLogger(SchemaController.class);

	@Autowired
	private SchemaService schemaService;

	@GetMapping("/schemas")
	public Collection<String> getSchemas() {
		return schemaService.getSchemas();
	}

	@GetMapping("/schema/{subject}")
	public SchemaMetadata getLatestSchema(@PathVariable String subject) {
		return schemaService.getLatestSchema(subject);
	}

	@GetMapping("/schema/{subject}/versions")
	public Collection<Integer> getAllVersions(@PathVariable String subject) {
		return schemaService.getAllVersions(subject);
	}

	@GetMapping("/schema/{subject}/compatibility")
	public String getCompatibility(@PathVariable String subject) {
		return schemaService.getCompatibility(subject);
	}

	@PostMapping("/schema")
	public Integer createSchema(@RequestBody SchemaRequestDto schema) {
		return schemaService.createSchema(schema);
	}

	@PostMapping("/schema")
	public Collection<Integer> createSchema(@RequestBody Collection<SchemaRequestDto> schemas) {
		return schemaService.createSchemas(schemas);
	}

	@PostMapping("/schema/{subject}/{compatibility}")
	public String updateCompatibility(@PathVariable String subject, @PathVariable String compatibility) {
		return schemaService.updateCompatibility(subject, compatibility);
	}

}
