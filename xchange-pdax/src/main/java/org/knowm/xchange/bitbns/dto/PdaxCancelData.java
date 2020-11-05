package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxCancelData {

	private PdaxCancelDataDetails data;

	public PdaxCancelData(@JsonProperty("data") PdaxCancelDataDetails data) {
		this.data = data;
	}

	public PdaxCancelDataDetails getData() {
		return data;
	}

	public void setData(PdaxCancelDataDetails data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PdaxCancelData [data=" + data + "]";
	}

}
