package com.xeiam.xchange.cryptsy;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class CryptsyTickerReturn {
	Map<String, CryptsyMarketCoin> markets;

	@JsonAnySetter
	public void anySetter(String key, Object value) {
		System.out.println(key + " = " + value.toString());
	}

	public Map<String, CryptsyMarketCoin> getMarkets() {
		return markets;
	}

	public void setMarkets(Map<String, CryptsyMarketCoin> markets) {
		this.markets = markets;
	}
}
