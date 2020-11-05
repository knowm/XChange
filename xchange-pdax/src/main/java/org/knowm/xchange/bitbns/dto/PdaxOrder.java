package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxOrder {

	private PdaxOrderDetails data;

	public PdaxOrder(@JsonProperty("data") PdaxOrderDetails data) {
		this.data = data;
	}

	public PdaxOrderDetails getData() {
		return data;
	}

	public void setData(PdaxOrderDetails data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PdaxOrder [data=" + data + "]";
	}

}
