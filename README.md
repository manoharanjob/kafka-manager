# kafka-admin-api

Provides REST endpoints for managing kafka platform.

## About

This application provides REST API for Kafka platform services administration.

This application utilize a Kafka admin client API to manage broker.

This application utilize a confluent dependency to manage schema registry.

This application utilize a Http client to manage Connect.

It can perform following operations:

### Topic
* List all available topics
* Create a topic
* Update a topic configurations
* Delete a topic
* Filter topics based on certain criteria
* Read messages from topic
* Write messages to topic

### Schema
* List all available schemas
* Create a schema
* Update a schema
* Delete a schema

### Acl
* List all available acls
* Create a acl
* Update a acl
* Delete a acl

### Connector
* List all available connectors
* Create a connector
* Update a connector
* Delete a connector

## Getting Started

### Prerequisites
There are a few things you need to have installed to run this project.

- [JDK 11+](https://openjdk.java.net/projects/jdk/11/)
- [Maven](https://maven.apache.org/)
- [Kafka](https://kafka.apache.org/downloads)

### Download

To run this project locally, first clone it with Git

```shell
git clone git@github.com:bf2fc6cc711aee1a0c2a/kafka-admin-api.git
cd kafka-admin-api
```

### Build

Now you can install the required dependencies with Maven

```shell
mvn clean install
```

### Run Kafka

Make sure Kafka running in your local or remote

### Configure

Configure kafka broker, schema registry and connect components details in application.yml

### Run Application

Run Spring Boot Main class to start an application

## API

After deployment to TAP the Kafka Admin API provides the following endpoints

### Topic
|URL   	                			|method  	|operation                          		|
|---	                			|---     	|---	                             		|
|/kafka/topics   	        		|GET     	|list the available topic names  	        |
|/kafka/topics/details   	    	|POST    	|list the available topics details 	        |
|/kafka/topic/{topicName}     		|GET     	|read topic details               			|
|/kafka/topic						|POST    	|create a topic								|
|/kafka/topics   					|POST    	|create more than one topic					|
|/kafka/topic/{topicName}   		|PUT    	|update topic configurations 				|
|/kafka/topics   					|PUT    	|update more than one topic configurations 	|
|/kafka/topic/{topicName}   		|DELETE		|delete topic								|
|/kafka/topics   	    			|DELETE    	|delete more than one topic 	            |


### Schema
|URL   	                			|method  	|operation                          				|
|---	                			|---     	|---	                             				|
|/kafka/schemas   	        		|GET     	|list the available schema subjects  	            |
|/kafka/schemas/details   	    	|POST    	|list the available schema details 	            	|
|/kafka/schema/{subject}     		|GET     	|read latest verion of schema subject details   	|
|/kafka/schema/{subject}/versions   |GET     	|read specific version of schema subject details    |
|/kafka/schema						|POST    	|create a schema									|
|/kafka/schemas   					|POST    	|create more than one schema						|
|/kafka/schema/{subject}	   		|DELETE		|delete all versions of specific schema				|
|/kafka/schema/{subject}/{version}  |DELETE    	|delete specific version of schema 	            	|

### Acl
|URL   	                			|method  	|operation                          				|
|---	                			|---     	|---	                             				|
|/kafka/acls   	        			|GET     	|list the available acls  	             			|
|/kafka/acls/filter   	    		|POST    	|list the available acls based on filter criteria 	|
|/kafka/acl							|POST    	|create a acl										|
|/kafka/acls   						|POST    	|create more than one acl							|
|/kafka/acl   						|DELETE		|delete acl											|
|/kafka/acls   	    				|DELETE    	|delete more than one acl 	            			|

### Connect
|URL   	                			|method  	|operation                          		|
|---	                			|---     	|---	                             		|
|/kafka/connectors   	        	|GET     	|list the available connectors  	        |
|/kafka/connector/{connector}     	|GET     	|read connector details               		|
|/kafka/connector					|POST    	|create a connector							|
|/kafka/connector/{connector}   	|DELETE		|delete a connector							|

## Swagger UI

You can work with the API via the SwaggerUI

Just go to the following URL and use the Swagger functionality

    http://localhost.8085/swagger-ui/index.html

