package com.marriott.eeh.controller;

import java.util.Collection;

import org.apache.kafka.common.acl.AclBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.dto.request.AclRequestDto;
import com.marriott.eeh.service.AclService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
public class AclController {

	private final Logger log = LoggerFactory.getLogger(AclController.class);

	@Autowired
	private AclService aclService;

	@ApiOperation(value = "Get topic names", 
		responseContainer = "ResponseEntity", 
		response = Collection.class, 
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic names retrieved successfully") 
	})
	@GetMapping("/acls")
	public ResponseEntity<Collection<AclBinding>> getAcls() {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.getAcls());
	}

	@ApiOperation(value = "Get topic names", 
		responseContainer = "ResponseEntity", 
		response = Collection.class, 
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Topic names retrieved successfully") 
	})
	@PostMapping("/acls/filter")
	public ResponseEntity<Collection<AclBinding>> getAclsByFilter(@RequestBody AclRequestDto acl) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.getAclsByFilter(acl));
	}
	
	@PostMapping("/acl")
	public ResponseEntity<Collection<AclBinding>> createAcl(@RequestBody AclRequestDto acl) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.createAcl(acl));
	}

	@PostMapping("/acls")
	public ResponseEntity<Collection<AclBinding>> createAcls(@RequestBody Collection<AclRequestDto> acls) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.createAcls(acls));
	}

	@DeleteMapping("/acl")
	public ResponseEntity<Collection<AclBinding>> deleteAcl(@RequestBody AclRequestDto acl) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.deleteAcl(acl));
	}

	@DeleteMapping("/acls")
	public ResponseEntity<Collection<AclBinding>> deleteAcls(@RequestBody Collection<AclRequestDto> acls) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.deleteAcls(acls));
	}
}
