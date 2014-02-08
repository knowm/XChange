package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoTradeAccountData {
	Map<String, BigDecimal> funds = new HashMap<String, BigDecimal>();

	public CryptoTradeAccountData(@JsonProperty("funds") Map<String, BigDecimal> someFunds) {
		
		funds = someFunds;
	}

	public Map<String, BigDecimal> getFunds() {
		return funds;
	}

	public void setFunds(Map<String, BigDecimal> funds) {
		this.funds = funds;
	}
}
