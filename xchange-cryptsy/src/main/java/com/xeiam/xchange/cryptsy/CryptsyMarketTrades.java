package com.xeiam.xchange.cryptsy;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyMarketTrades {

	Map<String, CryptsyMarketCoin> markets;

	public CryptsyMarketTrades(@JsonProperty("markets") Map<String, CryptsyMarketCoin> someMarkets) {
		markets = someMarkets;
	}
}
