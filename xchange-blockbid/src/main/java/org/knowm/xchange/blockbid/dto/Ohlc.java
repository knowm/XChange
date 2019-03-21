package org.knowm.xchange.blockbid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Ohlc {
	private final String timestamp;
	private final int open;
	private final int high;
	private final int low;
	private final int close;
	private final int volume;
	
	/**
	 * Constructor
	 * 
	 *  @param timestamp
	 *  @param open
	 *  @param high
	 *  @param low
	 *  @param close
	 *  @param volume
	 * */
	
	public Ohlc(
		@JsonProperty("timestamp") String timestamp,
		@JsonProperty("open") int open,
		@JsonProperty("high") int high,
		@JsonProperty("low") int low,
		@JsonProperty("close") int close,
		@JsonProperty("volume") int volume
	) {
		this.close = close;
		this.high = high;
		this.open = open;
		this.low = low;
		this.timestamp = timestamp;
		this.volume = volume;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public int getOpen() {
		return open;
	}
	public int getVolume() {
		return volume;
	}
	public int getClose() {
		return close;
	}
	public int getHigh() {
		return high;
	}
	public int getLow() {
		return low;
	}
	
	@Override
	public String toString() {
		return "OHLC [ timestamp="+timestamp+" open="+ open +" volume= "+ volume +" close="+ close +" high=" + high +" low= "+ low +"] \n";
	}
}