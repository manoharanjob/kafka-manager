package com.marriott.eeh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.dto.response.ClusterResponseDto;
import com.marriott.eeh.service.ClusterService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ClusterController {

	private final Logger log = LoggerFactory.getLogger(ClusterController.class);

	@Autowired
	private ClusterService clusterService;

	@ApiOperation(value = "Get Cluster details", response = ClusterResponseDto.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Cluster details are retrieved successfully"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/clusters")
	public ClusterResponseDto getClusters() {
		return clusterService.getClusters();

	}

}
