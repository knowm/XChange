package org.knowm.xchange.blockbid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Market {
	
	private final String id;
	private final String name;
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 */
	
	public Market( @JsonProperty("id") String id, @JsonProperty("name") String name ) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Market [id="+id+" name="+name+"] \n";
	}
}
