package com.marriott.eeh.controller;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.admin.TopicDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.dto.request.TopicRequestDto;
import com.marriott.eeh.dto.response.TopicResponseDto;
import com.marriott.eeh.kafka.TopicService;

@RestController
public class TopicController {

	private final Logger log = LoggerFactory.getLogger(TopicController.class);

	@Autowired
	private TopicService topicService;

	@GetMapping("/topics")
	public Collection<String> getTopics() {
		return topicService.getTopics();
	}

	@GetMapping("/topic/{topicName}")
	public Map<String, TopicDescription> getTopicDetails(@PathVariable String topicName) {
		return topicService.getTopicDetails(topicName);
	}

	@PostMapping("/topic")
	public Map<String, TopicDescription> getTopicDetails(@RequestBody Collection<String> topics) {
		return topicService.getTopicsDetails(topics);
	}

	@PostMapping("/topic")
	public Collection<TopicResponseDto> createTopic(@RequestBody TopicRequestDto topic) {
		return topicService.createTopic(topic);
	}

	@PostMapping("/topic")
	public Collection<TopicResponseDto> createTopics(@RequestBody Collection<TopicRequestDto> topics) {
		return topicService.createTopics(topics);
	}

	@DeleteMapping("/topic/{topicName}")
	public boolean deleteTopic(@PathVariable String topicName) {
		return topicService.deleteTopic(topicName);
	}

	@DeleteMapping("/topic")
	public boolean deleteTopics(@RequestBody Collection<String> topics) {
		return topicService.deleteTopics(topics);
	}

}
