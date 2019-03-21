package org.knowm.xchange.blockbid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Health {
	
	private final String uptime;
	/**
	 * Constructor
	 * 
	 * @param uptime
	 */
	
	public Health( @JsonProperty("uptime") String uptime ) {
		this.uptime = uptime;
	}

	public String getUptime() {
		return uptime;
	}
	
	@Override
	public String toString() {
		return " Health [uptime="+uptime+"]";
	}

}
