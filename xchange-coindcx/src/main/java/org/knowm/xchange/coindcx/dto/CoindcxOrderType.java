package org.knowm.xchange.coindcx.dto;

public enum CoindcxOrderType {
	MARKET("market_order"), 
	LIMIT("limit_order");

	private String value;
	
	private CoindcxOrderType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}
}
