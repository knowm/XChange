package org.knowm.xchange.bitso.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ravi
 *
 */
public class OrderBookPayload {

	private final String timestamp;
	private final String sequence;
	private final List<BitsoCommonOrderBook> bids;
	private final List<BitsoCommonOrderBook> asks;

	public OrderBookPayload(@JsonProperty("updated_at") String timestamp, @JsonProperty("sequence") String sequence,
			@JsonProperty("bids") List<BitsoCommonOrderBook> bids,
			@JsonProperty("asks") List<BitsoCommonOrderBook> asks) {
		this.timestamp = timestamp;
		this.sequence = sequence;
		this.asks = asks;
		this.bids = bids;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getSequence() {
		return sequence;
	}

	public List<BitsoCommonOrderBook> getBids() {
		return bids;
	}

	public List<BitsoCommonOrderBook> getAsks() {
		return asks;
	}

	@Override
	public String toString() {
		return "OrderBookPayload [timestamp=" + timestamp + ", sequence=" + sequence + ", bids=" + bids + ", asks="
				+ asks + "]";
	}

}
