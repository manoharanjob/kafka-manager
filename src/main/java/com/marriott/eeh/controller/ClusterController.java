package com.marriott.eeh.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.service.ClusterService;

@RestController
public class ClusterController {

	private final Logger log = LoggerFactory.getLogger(ClusterController.class);

	@Autowired
	private ClusterService clusterService;

	@GetMapping("/clusters")
	public Map<String, Object> getClusters() {
		return clusterService.getClusters();

	}

}
