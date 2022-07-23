package com.marriott.eeh.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.service.ConnectService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
public class ConnectController {

	private final Logger log = LoggerFactory.getLogger(ConnectController.class);

	@Autowired
	private ConnectService connectService;

	@ApiOperation(value = "Get topic names", 
		responseContainer = "ResponseEntity", 
		response = Collection.class, 
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic names retrieved successfully") 
	})
	@GetMapping("/connectors")
	public ResponseEntity<String> getConnectors() {
		return ResponseEntity.status(HttpStatus.OK).body(connectService.getConnectors());
	}

}
