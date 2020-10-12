package org.knowm.xchange.wazirx.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WazirxOrderBook {

	private long timestamp;
	private List<List<BigDecimal>> bids;
	private List<List<BigDecimal>> asks;

	public long getTimestamp() {
		return timestamp;
	}

	public List<List<BigDecimal>> getBids() {
		return bids;
	}

	public void setBids(List<List<BigDecimal>> bids) {
		this.bids = bids;
	}

	public List<List<BigDecimal>> getAsks() {
		return asks;
	}

	public void setAsks(List<List<BigDecimal>> asks) {
		this.asks = asks;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "WazirxOrderBook [timestamp=" + timestamp + ", bids=" + bids + ", asks=" + asks + "]";
	}

}
