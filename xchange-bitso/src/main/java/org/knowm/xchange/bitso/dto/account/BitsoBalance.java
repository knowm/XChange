package org.knowm.xchange.bitso.dto.account;

import org.knowm.xchange.bitso.dto.trade.BitsoErrorDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/** @author Matija Mazi */
public final class BitsoBalance {

	private boolean success;
	private BalancePayload payload;
	private String error;

	public BitsoBalance(@JsonProperty("success") boolean success, @JsonProperty("payload") BalancePayload payload,
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

	public BalancePayload getPayload() {
		return payload;
	}

	public void setPayload(BalancePayload payload) {
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
		return "BitsoBalance [success=" + success + ", payload=" + payload + ", error=" + error + "]";
	}

}
