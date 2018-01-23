package org.xchange.bitz.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZTicker {
	
  private final BigDecimal last;
	private final BigDecimal high;
	private final BigDecimal low;
	private final BigDecimal volume;
	private final BigDecimal buy;
	private final BigDecimal sell;
	private final long timestamp;
	
	public BitZTicker(@JsonProperty("last") BigDecimal last, @JsonProperty("high")BigDecimal high, 
			@JsonProperty("low") BigDecimal low, @JsonProperty("vol") BigDecimal volume, 
			@JsonProperty("buy") BigDecimal buy, @JsonProperty("sell") BigDecimal sell, 
			@JsonProperty("date") long timestamp) {
		
		this.last = last;
		this.high = high;
		this.low = low;
		this.volume = volume;
		this.buy = buy;
		this.sell = sell;
		this.timestamp = timestamp;
	}

	public BigDecimal getLast() {
		return last;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	// TODO: Generate toString Override
	@Override
	public String toString() {
	  return String.format("BitZTicker[last=%s, high=%s, low=%s, buy=%s, sell=%s, volume=%s, timestamp=%d]", 
	      getLast().toString(), getHigh().toString(), getLow().toString(), getBuy().toString(), 
	      getSell().toString(), getVolume().toString(), getTimestamp());
	}
}
