package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiTickerVolume {

	public final BigDecimal btc;
	public final BigDecimal usd;
	public final float timestamp;

	/**
	 * @param btc
	 * @param usd
	 * @param timestamp
	 */
	public GeminiTickerVolume(@JsonProperty("btc") BigDecimal btc, @JsonProperty("usd") BigDecimal usd,
		@JsonProperty("timestamp") float timestamp) {

		this.btc = btc;
		this.usd = usd;
		this.timestamp = timestamp;
	}
	
	public BigDecimal getBtc() {

		return btc;
	}
	
	public BigDecimal getUsd() {

		return usd;
	}
	
	public float getTimestamp() {

		return timestamp;
	}

	@Override
	public String toString() {

		return "BitfinexTicker [btc=" + btc + ", usd=" + usd
				+ ", timestamp=" + timestamp + "]";
	}
}
