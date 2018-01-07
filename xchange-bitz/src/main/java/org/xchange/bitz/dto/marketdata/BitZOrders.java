package org.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZOrders {
	
	private final BitZPublicOrder[] asks;
	private final BitZPublicOrder[] bids;
	private final long timestamp;

	public BitZOrders(@JsonProperty("asks") BitZPublicOrder[] asks, @JsonProperty("bids") BitZPublicOrder[] bids, 
			@JsonProperty("date") long timestamp) {
		
		this.asks = asks;
		this.bids = bids;
		this.timestamp = timestamp;
	}

	public BitZPublicOrder[] getAsks() {
		return asks;
	}

	public BitZPublicOrder[] getBids() {
		return bids;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
