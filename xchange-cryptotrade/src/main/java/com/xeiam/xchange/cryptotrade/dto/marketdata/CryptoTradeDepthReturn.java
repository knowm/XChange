package com.xeiam.xchange.cryptotrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoTradeDepthReturn {

	String success;
	CryptoTradeDepth depth;
	public CryptoTradeDepthReturn(@JsonProperty("status") String success, @JsonProperty("data") CryptoTradeDepth depth) {
		super();
		this.success = success;
		this.depth = depth;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public CryptoTradeDepth getDepth() {
		return depth;
	}
	public void setDepth(CryptoTradeDepth depth) {
		this.depth = depth;
	}
	
	
}
