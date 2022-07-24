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
public class Partition {

	private int partition;
	private int leader;
	private Collection<Integer> replicas;
	private Collection<Integer> isr;

}
