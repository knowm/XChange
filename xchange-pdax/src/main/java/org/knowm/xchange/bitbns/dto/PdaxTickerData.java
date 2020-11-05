package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxTickerData {

	private PdaxTicker data;
	
	public PdaxTickerData(@JsonProperty("data") PdaxTicker data) {
		this.data=data;
	}

	public PdaxTicker getData() {
		return data;
	}

	public void setData(PdaxTicker data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PdaxTickerData [data=" + data + "]";
	}

}
