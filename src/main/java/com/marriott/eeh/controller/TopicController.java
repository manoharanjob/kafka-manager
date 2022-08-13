package com.marriott.eeh.controller;

import java.util.Collection;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.constant.Env;
import com.marriott.eeh.dto.request.TopicRequestDto;
import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.service.TopicService;
import com.marriott.eeh.validator.constraint.EnvType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@RestController
public class TopicController {

	private final Logger log = LoggerFactory.getLogger(TopicController.class);

	@Autowired
	private TopicService topicService;

	@ApiOperation(value = "Get all topics", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topics are retrieved successfully"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/{env}/topics")
	public ResponseEntity<Collection<String>> getTopics(@Valid @EnvType @PathVariable Env env, @Valid @EnvType @RequestParam Env env1) {
		return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics());
	}

	@ApiOperation(value = "Get given topic details", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic details are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping("/topics/details")
	public Collection<TopicResponseDto> getTopicDetails(
			@ApiParam(name = "topics", type = "Collection<String>", value = "list of topic names", required = true) @RequestBody Collection<String> topics) {
		return topicService.getTopicsDetails(topics);
	}

	@ApiOperation(value = "Get given topic configs", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic configs are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping("/topics/configs")
	public Collection<TopicResponseDto> getTopicsConfigs(
			@ApiParam(name = "topics", type = "Collection<String>", value = "list of topic names", required = true) @RequestBody Collection<String> topics) {
		return topicService.getTopicsConfigs(topics);
	}

	@ApiOperation(value = "Get given topic details", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic details are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/topic/{topicName}")
	public Collection<TopicResponseDto> getTopicDetails(
			@ApiParam(name = "topicName", type = "String", value = "Topic name", required = true) @PathVariable String topicName) {
		return topicService.getTopicDetails(topicName);
	}

	@ApiOperation(value = "Get given topic configs", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic configs are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/topic/{topicName}/config")
	public Collection<TopicResponseDto> getTopicConfigs(
			@ApiParam(name = "topicName", type = "String", value = "Topic name", required = true) @PathVariable String topicName) {
		return topicService.getTopicConfigs(topicName);
	}

	@ApiOperation(value = "Create new topic", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic is created successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping("/topic")
	public Collection<TopicResponseDto> createTopic(
			@ApiParam(name = "topic", type = "TopicRequestDto", value = "topic object", required = true) @Valid @RequestBody TopicRequestDto topic) {
		return topicService.createTopic(topic);
	}

	@ApiOperation(value = "Create new topics", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic are created successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping("/topics")
	public Collection<TopicResponseDto> createTopics(
			@ApiParam(name = "topics", type = "Collection<TopicRequestDto>", value = "List of topic object", required = true) @RequestBody Collection<TopicRequestDto> topics) {
		return topicService.createTopics(topics);
	}

	@ApiOperation(value = "Update topic configs", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic configs are updated successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PutMapping("/topic")
	public Collection<TopicResponseDto> updateTopic(
			@ApiParam(name = "topic", type = "TopicRequestDto", value = "topic object", required = true) @RequestBody TopicRequestDto topic) {
		return topicService.updateTopicConfig(topic);
	}

	@ApiOperation(value = "Update more than one topic configs", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic configs are updated successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@PutMapping("/topics")
	public Collection<TopicResponseDto> updateTopics(
			@ApiParam(name = "topics", type = "Collection<TopicRequestDto>", value = "List of topic object", required = true) @RequestBody Collection<TopicRequestDto> topics) {
		return topicService.updateTopicsConfig(topics);
	}

	@ApiOperation(value = "Delete a topic", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic is deleted successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@DeleteMapping("/topic/{topicName}")
	public boolean deleteTopic(
			@ApiParam(name = "topicName", type = "String", value = "Topic name", required = true) @PathVariable String topicName) {
		return topicService.deleteTopic(topicName);
	}

	@ApiOperation(value = "Delete given topics", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic are deleted successfully"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@DeleteMapping("/topics")
	public boolean deleteTopics(
			@ApiParam(name = "topics", type = "Collection<String>", value = "List of topic names", required = true) @RequestBody Collection<String> topics) {
		return topicService.deleteTopics(topics);
	}

}
