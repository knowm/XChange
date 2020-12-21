package org.knowm.xchange.bitso.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class BitsoAllOrders {

	private String success;
	private List<Payload> payload;
	private String error;

	public BitsoAllOrders(@JsonProperty("success") String success, @JsonProperty("payload") List<Payload> payload,
			@JsonProperty("error") @JsonDeserialize(using = BitsoErrorDeserializer.class) String errorMessage) {
		this.success = success;
		this.payload = payload;
		this.error = errorMessage;

	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<Payload> getPayload() {
		return payload;
	}

	public void setPayload(List<Payload> payload) {
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
		return "BitsoAllOrders [success=" + success + ", payload=" + payload + ", error=" + error + "]";
	}

}
