package com.marriott.eeh.client;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.AlterConfigsResult;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.clients.admin.CreatePartitionsResult;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteAclsResult;
import org.apache.kafka.clients.admin.DeleteConsumerGroupsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeAclsResult;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclBindingFilter;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {

	private final Logger log = LoggerFactory.getLogger(KafkaClient.class);

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

	public DescribeConfigsResult getTopicsConfig(Collection<ConfigResource> topics) {
		return adminClient.describeConfigs(topics);
	}

	public CreateTopicsResult createTopics(Collection<NewTopic> topics) {
		return adminClient.createTopics(topics);
	}

	public CreatePartitionsResult createPartitions(Map<String, NewPartitions> partitions) {
		return adminClient.createPartitions(partitions);
	}

	public AlterConfigsResult updateTopicsConfig(Map<ConfigResource, Collection<AlterConfigOp>> configs) {
		return adminClient.incrementalAlterConfigs(configs);
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

	public DescribeAclsResult getAcls() {
		return adminClient.describeAcls(AclBindingFilter.ANY);
	}

	public DescribeAclsResult getAcl(AclBindingFilter acl) {
		return adminClient.describeAcls(acl);
	}

	public CreateAclsResult createAcls(Collection<AclBinding> acls) {
		return adminClient.createAcls(acls);
	}

	public DeleteAclsResult deleteAcls(Collection<AclBindingFilter> acls) {
		return adminClient.deleteAcls(acls);
	}
}
