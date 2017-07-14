package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiTicker {

	private final BigDecimal bid;
	private final BigDecimal ask;
	private final BigDecimal last;
	private final GeminiTickerVolume volume;

	/**
	 * @param bid
	 * @param ask
	 * @param timestamp
	 * @param volume
	 */
	public GeminiTicker(@JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask,
			@JsonProperty("volume") GeminiTickerVolume volume, @JsonProperty("last") BigDecimal last) {

		this.bid = bid;
		this.ask = ask;
		this.last = last;
		this.volume = volume;
	}


	public BigDecimal getBid() {

		return bid;
	}

	public BigDecimal getAsk() {

		return ask;
	}

	public BigDecimal getLast_price() {

		return last;
	}

	public GeminiTickerVolume getVolume() {

		return volume;
	}

	@Override
	public String toString() {

		return "BitfinexTicker [bid=" + bid + ", ask=" + ask
				+ ", last=" + last + "]";
	}

}
