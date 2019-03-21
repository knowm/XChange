package org.knowm.xchange.blockbid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Currency {
	private final String id;
	private final String symbol;
	private final String type;
	private final int precision;
	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getSymbol() {
		return symbol;
	}
	public int getPrecision() {
		return precision;
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param type
	 * @param symbol
	 * @param precision
	 * 
	 * */
	
	public Currency(
		@JsonProperty("id") String id,
		@JsonProperty("type") String type,
		@JsonProperty("symbol") String symbol,
		@JsonProperty("precision") int precision
			) {
		this.id = id;
		this.type = type;
		this.symbol = symbol;
		this.precision = precision;
	}
	
	@Override
	public String toString() {
		return "Curreny [ id="+id+" type="+type+" symbol="+symbol+" precision="+precision+" ] \n";
	}
}


