package org.knowm.xchange.coindcx.dto;

import java.math.BigDecimal;
import java.util.Map;

public class CoindcxOrderBook {

	private Map<BigDecimal, BigDecimal> bids;
	private Map<BigDecimal, BigDecimal> asks;

	public Map<BigDecimal, BigDecimal> getBids() {
		return bids;
	}

	public void setBids(Map<BigDecimal, BigDecimal> bids) {
		this.bids = bids;
	}

	public Map<BigDecimal, BigDecimal> getAsks() {
		return asks;
	}

	public void setAsks(Map<BigDecimal, BigDecimal> asks) {
		this.asks = asks;
	}

	@Override
	public String toString() {
		return "CoindcxOrderBook [bids=" + bids + ", asks=" + asks + "]";
	}

}
