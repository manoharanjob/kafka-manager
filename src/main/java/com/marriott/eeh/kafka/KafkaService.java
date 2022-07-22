package com.marriott.eeh.kafka;

import java.util.Collection;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteConsumerGroupsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

	private final Logger log = LoggerFactory.getLogger(KafkaService.class);

	@Autowired
	private AdminClient adminClient;

	/*-
	  Lists all the topic 
	  Accepts : AdminClient Object 
	  Returns : List of topic name as String
	 */
	public ListTopicsResult getTopics() {
		return adminClient.listTopics();
	}

	public DescribeTopicsResult getTopicsDetails(Collection<String> topics) {
		return adminClient.describeTopics(topics);
	}

	public CreateTopicsResult createTopics(Collection<NewTopic> topics) {
		return adminClient.createTopics(topics);
	}

	public DeleteTopicsResult deleteTopics(Collection<String> topics) {
		return adminClient.deleteTopics(topics);
	}

	public ListConsumerGroupsResult getConsumerGroups() {
		return adminClient.listConsumerGroups();
	}

	public DescribeConsumerGroupsResult getConsumerGroupsDetails(Collection<String> groupIds) {
		return adminClient.describeConsumerGroups(groupIds);
	}

	public DeleteConsumerGroupsResult deleteConsumerGroups(Collection<String> groupIds) {
		return adminClient.deleteConsumerGroups(groupIds);
	}

	public DescribeClusterResult getClusters() {
		return adminClient.describeCluster();
	}

}
