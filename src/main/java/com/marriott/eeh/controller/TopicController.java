package com.marriott.eeh.controller;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.admin.TopicDescription;
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

import com.marriott.eeh.dto.request.TopicRequestDto;
import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.service.TopicService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
public class TopicController {

	private final Logger log = LoggerFactory.getLogger(TopicController.class);

	@Autowired
	private TopicService topicService;

	@ApiOperation(
			value="Get topic names",
			responseContainer = "ResponseEntity",
			response = Collection.class,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Topic names retrieved successfully")
	})
	@GetMapping("/topics")
	public ResponseEntity<Collection<String>> getTopics() {
		return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics());
	}

	@PostMapping("/topics/details")
	public Map<String, TopicDescription> getTopicDetails(@RequestBody Collection<String> topics) {
		return topicService.getTopicsDetails(topics);
	}

	@GetMapping("/topic/{topicName}")
	public Map<String, TopicDescription> getTopicDetails(
			@ApiParam(name = "topicName", type = "String", value = "Topic name", required = true) @PathVariable String topicName) {
		return topicService.getTopicDetails(topicName);
	}

	@PostMapping("/topic")
	public Collection<TopicResponseDto> createTopic(@RequestBody TopicRequestDto topic) {
		return topicService.createTopic(topic);
	}

	@PostMapping("/topics")
	public Collection<TopicResponseDto> createTopics(@RequestBody Collection<TopicRequestDto> topics) {
		return topicService.createTopics(topics);
	}

	@DeleteMapping("/topic/{topicName}")
	public boolean deleteTopic(@PathVariable String topicName) {
		return topicService.deleteTopic(topicName);
	}

	@DeleteMapping("/topics")
	public boolean deleteTopics(@RequestBody Collection<String> topics) {
		return topicService.deleteTopics(topics);
	}

}
