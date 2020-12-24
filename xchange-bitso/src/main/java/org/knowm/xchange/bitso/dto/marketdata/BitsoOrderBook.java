package org.knowm.xchange.bitso.dto.marketdata;

import org.knowm.xchange.bitso.dto.trade.BitsoErrorDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

 
/**
 * @author Ravi Pandit
 *
 */
public class BitsoOrderBook {

	private final boolean success;
	private final OrderBookPayload payload;
	private String error;

	/**
	 * Constructor
	 *
	 * @param timestamp
	 * @param bids
	 * @param asks
	 */
	public BitsoOrderBook(@JsonProperty("success") boolean success, @JsonProperty("payload") OrderBookPayload payload,
			@JsonProperty("error") @JsonDeserialize(using = BitsoErrorDeserializer.class) String errorMessage) {
		this.success = success;
		this.payload = payload;
		this.error = errorMessage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public OrderBookPayload getPayload() {
		return payload;
	}

	@Override
	public String toString() {
		return "BitsoOrderBook [success=" + success + ", payload=" + payload + ", error=" + error + "]";
	}

}
