package com.marriott.eeh.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.dto.request.SchemaRequestDto;
import com.marriott.eeh.service.SchemaService;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class SchemaController {

	private final Logger log = LoggerFactory.getLogger(SchemaController.class);

	@Autowired
	private SchemaService schemaService;

	@ApiOperation(value = "Get all schema names", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema names are retrieved successfully"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/schemas")
	public Collection<String> getSchemas() {
		return schemaService.getSchemas();
	}

	@ApiOperation(value = "Get schema details", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema details are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/schema/{subject}")
	public SchemaMetadata getLatestSchema(
			@ApiParam(name = "subject", type = "String", value = "Subject name", required = true) @PathVariable String subject) {
		return schemaService.getLatestSchema(subject);
	}

	@ApiOperation(value = "Get schema versions", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema versions are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/schema/{subject}/versions")
	public Collection<Integer> getAllVersions(
			@ApiParam(name = "subject", type = "String", value = "Subject name", required = true) @PathVariable String subject) {
		return schemaService.getAllVersions(subject);
	}

	@ApiOperation(value = "Get schema compatibility", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema compatibility is retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/schema/{subject}/compatibility")
	public String getCompatibility(
			@ApiParam(name = "subject", type = "String", value = "Subject name", required = true) @PathVariable String subject) {
		return schemaService.getCompatibility(subject);
	}

	@ApiOperation(value = "Create a schema", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema is created successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/schema")
	public Integer createSchema(
			@ApiParam(name = "schema", type = "SchemaRequestDto", value = "SchemaRequestDto object", required = true) @RequestBody SchemaRequestDto schema) {
		return schemaService.createSchema(schema);
	}

	@ApiOperation(value = "Create more than one schema", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schemas are created successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/schemas")
	public Collection<Integer> createSchema(
			@ApiParam(name = "schemas", type = "Collection<SchemaRequestDto>", value = "List of SchemaRequestDto object", required = true) @RequestBody Collection<SchemaRequestDto> schemas) {
		return schemaService.createSchemas(schemas);
	}

	@ApiOperation(value = "Update schema compatibility", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema compatibility is updated successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/schema/{subject}/{compatibility}")
	public String updateCompatibility(
			@ApiParam(name = "subject", type = "String", value = "Subject name", required = true) @PathVariable String subject,
			@ApiParam(name = "compatibility", type = "String", value = "Compatibility name", required = true) @PathVariable String compatibility) {
		return schemaService.updateCompatibility(subject, compatibility);
	}

	@ApiOperation(value = "Delete a schema", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schema is deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/schema/{subject}/{version}")
	public Integer deleteSchemaByVersion(
			@ApiParam(name = "subject", type = "String", value = "Subject name", required = true) @PathVariable String subject,
			@ApiParam(name = "version", type = "String", value = "Version number", required = true) @PathVariable String version) {
		return schemaService.deleteSchema(subject, version);
	}

	@ApiOperation(value = "Delete all versions of specific schema", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Schemas are deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/schema/{subject}")
	public Collection<Integer> deleteSchema(
			@ApiParam(name = "subject", type = "String", value = "Subject name", required = true) @PathVariable String subject) {
		return schemaService.deleteSchemas(subject);
	}

}
