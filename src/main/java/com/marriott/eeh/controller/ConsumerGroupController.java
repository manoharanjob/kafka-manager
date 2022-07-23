package com.marriott.eeh.controller;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.service.ConsumerGroupService;

@RestController
public class ConsumerGroupController {

	private final Logger log = LoggerFactory.getLogger(ConsumerGroupController.class);

	@Autowired
	private ConsumerGroupService consumerGroupService;

	@GetMapping("/consumer-groups")
	public Collection<ConsumerGroupListing> getConsumerGroups() {
		return consumerGroupService.getConsumerGroups();
	}

	@PostMapping("/consumer-groups/details")
	public Map<String, ConsumerGroupDescription> getConsumerGroupsDetails(
			@RequestBody Collection<String> consumerGroups) {
		return consumerGroupService.getConsumerGroupsDetails(consumerGroups);
	}

	@GetMapping("/consumer-group/{consumerGroup}")
	public Map<String, ConsumerGroupDescription> getConsumerGroupDetails(@PathVariable String consumerGroup) {
		return consumerGroupService.getConsumerGroupDetails(consumerGroup);
	}

	@DeleteMapping("/consumer-group/{consumerGroup}")
	public boolean deleteConsumerGroup(@PathVariable String consumerGroup) {
		return consumerGroupService.deleteConsumerGroup(consumerGroup);
	}

	@DeleteMapping("/consumer-groups")
	public boolean deleteConsumerGroups(@RequestBody Collection<String> consumerGroups) {
		return consumerGroupService.deleteConsumerGroups(consumerGroups);
	}
}
