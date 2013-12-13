package com.xeiam.xchange.cryptsy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyMarketCoin {
	// "marketid":"43","label":"AMC\/BTC","lasttradeprice":"0.00002010","volume":"1516.21251688","lasttradetime":"2013-07-02
	// 23:31:37","primaryname":"AmericanCoin","primarycode":"AMC","secondaryname":"BitCoin","secondarycode":"BTC","recenttrades":
	List<RecentTrade> recentTrades;
	String marketId;
	String primaryCode;
	String secondaryCode;

	public CryptsyMarketCoin(@JsonProperty("recenttrades") List<RecentTrade> recentTrades, @JsonProperty("marketid") String aMarketId, @JsonProperty("primarycode") String aPrimaryCode, @JsonProperty("secondarycode") String aSecondaryCode) {
		this.recentTrades = recentTrades;
		marketId = aMarketId;
		primaryCode = aPrimaryCode;
		secondaryCode = aSecondaryCode;
	}

	public List<RecentTrade> getRecentTrades() {
		return recentTrades;
	}

	public void setRecentTrades(List<RecentTrade> recentTrades) {
		this.recentTrades = recentTrades;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getPrimaryCode() {
		return primaryCode;
	}

	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}

	public String getSecondaryCode() {
		return secondaryCode;
	}

	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
	}

}
