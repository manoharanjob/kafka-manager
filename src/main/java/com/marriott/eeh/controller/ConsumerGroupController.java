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

import com.marriott.eeh.dto.response.ConsumerGroupResponseDto;
import com.marriott.eeh.service.ConsumerGroupService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ConsumerGroupController {

	private final Logger log = LoggerFactory.getLogger(ConsumerGroupController.class);

	@Autowired
	private ConsumerGroupService consumerGroupService;

	@ApiOperation(value = "Get all consumer groups", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Consumer groups are retrieved successfully"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/consumer-groups")
	public Collection<ConsumerGroupResponseDto> getConsumerGroups() {
		return consumerGroupService.getConsumerGroups();
	}

	@ApiOperation(value = "Get given consumer groups details", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Consumer groups details are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/consumer-groups/details")
	public Collection<ConsumerGroupResponseDto> getConsumerGroupsDetails(
			@ApiParam(name = "consumerGroups", type = "Collection<String>", value = "List of consumer group names", required = true) @RequestBody Collection<String> consumerGroups) {
		return consumerGroupService.getConsumerGroupsDetails(consumerGroups);
	}

	@ApiOperation(value = "Get consumer group details", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Consumer group details are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/consumer-group/{consumerGroup}")
	public Collection<ConsumerGroupResponseDto> getConsumerGroupDetails(
			@ApiParam(name = "consumerGroup", type = "String", value = "Consumer group name", required = true) @PathVariable String consumerGroup) {
		return consumerGroupService.getConsumerGroupDetails(consumerGroup);
	}

	@ApiOperation(value = "Delete consumer group", response = Boolean.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Consumer group is deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/consumer-group/{consumerGroup}")
	public boolean deleteConsumerGroup(
			@ApiParam(name = "consumerGroup", type = "String", value = "Consumer group name", required = true) @PathVariable String consumerGroup) {
		return consumerGroupService.deleteConsumerGroup(consumerGroup);
	}

	@ApiOperation(value = "Delete given consumer groups", response = Boolean.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Consumer groups are deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/consumer-groups")
	public boolean deleteConsumerGroups(
			@ApiParam(name = "consumerGroups", type = "Collection<String>", value = "List of consumer group names", required = true) @RequestBody Collection<String> consumerGroups) {
		return consumerGroupService.deleteConsumerGroups(consumerGroups);
	}
	
}
