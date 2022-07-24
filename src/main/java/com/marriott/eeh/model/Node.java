package com.marriott.eeh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Node {

	private int id;
	private String idString;
	private String host;
	private int port;
	private String rack;

}
