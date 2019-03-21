package org.knowm.xchange.blockbid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Trade {
	private final int id;
	private final float price;
	private final float volume;
	private final String market;
	private final String createdAt;
	private final String side;
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param price
	 * @param volume
	 * @param market
	 * @param createdAt
	 * @param side
	 * 
	 **/
	
	public Trade(
		@JsonProperty("id") int id,
		@JsonProperty("price") float price,
		@JsonProperty("volume") float volume,
		@JsonProperty("market") String market,
		@JsonProperty("createdAt") String createdAt,
		@JsonProperty("side") String side
			) {
		this.id = id;
		this.price = price;
		this.volume = volume;
		this.market = market;
		this.createdAt = createdAt;
		this.side = side;
	}
	
	public int getId() {
		return id;
	}
	public float getPrice() {
		return price;
	}
	public float getVolume() {
		return volume;
	}
	public String getMarket() {
		return market;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public String getSide() {
		return side;
	}	
	
	@Override
	public String toString() {
		return "Trade [ id="+id+" price="+price+" volume="+volume+" market="+market+" createdAt="+createdAt+" side="+side+" ] \n";
	}
}