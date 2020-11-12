package org.knowm.xchange.bitbns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxOrderPlaceStatusResponse {

	private PdaxOrderResponse data;

	public PdaxOrderPlaceStatusResponse(@JsonProperty("data") PdaxOrderResponse data) {
		this.data=data;
	}

	public PdaxOrderResponse getData() {
		return data;
	}

	public void setData(PdaxOrderResponse data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PdaxOrderPlaceStatusResponse [data=" + data + "]";
	}

}
