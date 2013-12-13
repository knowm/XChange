package com.xeiam.xchange.cryptsy;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyTicker {

	String success;
	public CryptsyTickerReturn result;

	public CryptsyTicker(@JsonProperty("success") String aSuccess, @JsonProperty("return") CryptsyTickerReturn someMarkets) {
		success = aSuccess;
		result = someMarkets;
	}

	@JsonAnySetter
	public void anySetter(String key, Object value) {
		System.out.println(key + " = " + value.toString());
	}
}
