package org.knowm.xchange.blockbid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Ticker {
	private final String timestamp;
	private final String market;
	private final int last;
	
	/**
	 * Constructor
	 * 
	 * @param timestamp
	 * @param market
	 * @param last
	 */
	
	public Ticker( @JsonProperty("timestamp") String timestamp, 
			@JsonProperty("market") String market, @JsonProperty("last") int last  ) {
		this.timestamp = timestamp;
		this.market= market;
		this.last = last;
	}
	
	public String getTicker() {
		return timestamp;
	}
	public int getLast() {
		return last;
	}
	public String getMarket() {
		return market;
	}
	
	@Override
	public String toString() {
		return " Ticker [market="+market+" last="+last+" timestamp="+timestamp+"]\n";
	}
}
