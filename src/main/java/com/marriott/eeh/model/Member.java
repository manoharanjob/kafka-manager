package com.marriott.eeh.model;

import java.util.Collection;

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
public class Member {

	private String memberId;
	private String groupInstanceId;
	private String clientId;
	private String host;
	private Collection<Topic> topics;

}
