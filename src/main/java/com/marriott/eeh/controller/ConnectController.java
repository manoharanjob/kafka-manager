package com.marriott.eeh.controller;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.service.ConnectService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
public class ConnectController {

	private final Logger log = LoggerFactory.getLogger(ConnectController.class);

	@Autowired
	private ConnectService connectService;

	@ApiOperation(value = "Get all connectors names", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Connectors names are retrieved successfully"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/connectors")
	public ResponseEntity<String> getConnectors() {
		return ResponseEntity.status(HttpStatus.OK).body(connectService.getConnectors());
	}

	@ApiOperation(value = "Get given connector details", response = Map.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Connector details are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/connector/{connector}")
	public ResponseEntity<Map> getConnector(
			@ApiParam(name = "connector", type = "String", value = "Connector name", required = true) @PathVariable String connector) {
		return ResponseEntity.status(HttpStatus.OK).body(connectService.getConnector(connector));
	}
	
	@ApiOperation(value = "Create new connector", response = Map.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Connector is created successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/connector")
	public ResponseEntity<Map> createConnector(
			@ApiParam(name = "connector", type = "Map", value = "Connector config details", required = true) @RequestBody Map<String, Object> connector) {
		return ResponseEntity.status(HttpStatus.OK).body(connectService.createConnector(connector));
	}
	
	@ApiOperation(value = "Delete a connector", response = Map.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Connector is deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/connector/{connector}")
	public ResponseEntity<String> deleteConnector(
			@ApiParam(name = "connector", type = "String", value = "Connector name", required = true) @PathVariable String connector) {
		return ResponseEntity.status(HttpStatus.OK).body(connectService.deleteConnector(connector));
	}
	
}
