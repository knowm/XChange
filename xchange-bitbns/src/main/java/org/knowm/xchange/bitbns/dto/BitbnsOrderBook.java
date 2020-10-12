package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitbnsOrderBook {

	private BigDecimal rate;
	private long btc;
	
	public BitbnsOrderBook(
			@JsonProperty("rate")BigDecimal rate,
			@JsonProperty("btc")long btc) {
		this.rate=rate;
		this.btc=btc;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public long getBtc() {
		return btc;
	}

	public void setBtc(long btc) {
		this.btc = btc;
	}

	@Override
	public String toString() {
		return "BitbnsOrderBook [rate=" + rate + ", btc=" + btc + "]";
	}

}
