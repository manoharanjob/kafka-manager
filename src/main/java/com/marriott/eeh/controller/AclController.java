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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marriott.eeh.dto.request.AclRequestDto;
import com.marriott.eeh.dto.response.AclResponseDto;
import com.marriott.eeh.service.AclService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@RestController
public class AclController {

	private final Logger log = LoggerFactory.getLogger(AclController.class);

	@Autowired
	private AclService aclService;

	@ApiOperation(value = "Get all Acls", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Acls are retrieved successfully"),
		@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping("/acls")
	public ResponseEntity<Collection<AclResponseDto>> getAcls() {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.getAcls());
	}

	@ApiOperation(value = "Get all Acls by filter", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Acls are retrieved successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/acls/filter")
	public ResponseEntity<Collection<AclResponseDto>> getAclsByFilter(
			@ApiParam(name = "acl", type = "AclRequestDto", value = "acl object", required = true) @RequestBody AclRequestDto acl) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.getAclsByFilter(acl));
	}
	
	@ApiOperation(value = "Create Acl", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Acl is created successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/acl")
	public ResponseEntity<Collection<AclResponseDto>> createAcl(
			@ApiParam(name = "acl", type = "AclRequestDto", value = "acl object", required = true) @Valid @RequestBody AclRequestDto acl) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.createAcl(acl));
	}

	@ApiOperation(value = "Create more than one Acls", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 201, message = "Acls are created successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/acls")
	public ResponseEntity<Collection<AclResponseDto>> createAcls(
			@ApiParam(name = "acls", type = "Collection<AclRequestDto>", value = "list of Acls object", required = true) @RequestBody Collection<AclRequestDto> acls) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.createAcls(acls));
	}

	@ApiOperation(value = "Delete Acl", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Acl is deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/acl")
	public ResponseEntity<Collection<AclResponseDto>> deleteAcl(
			@ApiParam(name = "acl", type = "AclRequestDto", value = "acl object", required = true) @RequestBody AclRequestDto acl) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.deleteAcl(acl));
	}

	@ApiOperation(value = "Delete more than one Acl", response = Collection.class, responseContainer = "ResponseEntity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "Acls are deleted successfully"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@DeleteMapping("/acls")
	public ResponseEntity<Collection<AclResponseDto>> deleteAcls(
			@ApiParam(name = "acls", type = "Collection<AclRequestDto>", value = "list of Acls object", required = true) @RequestBody Collection<AclRequestDto> acls) {
		return ResponseEntity.status(HttpStatus.OK).body(aclService.deleteAcls(acls));
	}
	
}
