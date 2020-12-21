package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Ravi Pandit
 *
 */
public class BitsoOrderResponse {

	private String success;
	private NewOrderResponse payload;
	private String error;

	public BitsoOrderResponse(@JsonProperty("success") String success,
			@JsonProperty("payload") NewOrderResponse payload,
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

	public NewOrderResponse getPayload() {
		return payload;
	}

	public void setPayload(NewOrderResponse payload) {
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
		return "BitsoAddOrderResponse [success=" + success + ", payload=" + payload + ", error=" + error + "]";
	}

}
