package org.knowm.xchange.bitso.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class BitsoCacleOrderResponse {
	private boolean success;
	private List<String> payload;
	private String error;

	public BitsoCacleOrderResponse(@JsonProperty("success") boolean success,
			@JsonProperty("payload") List<String> payload,
			@JsonProperty("error") @JsonDeserialize(using = BitsoErrorDeserializer.class) String errorMessage) {
		this.success = success;
		this.payload = payload;
		this.error = errorMessage;

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getPayload() {
		return payload;
	}

	public void setPayload(List<String> payload) {
		this.payload = payload;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "BitsoCacleOrderResponse [success=" + success + ", payload=" + payload + ", error=" + error + "]";
	}

}
