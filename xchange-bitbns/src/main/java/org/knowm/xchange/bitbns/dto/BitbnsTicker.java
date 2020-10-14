package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitbnsTicker {

	private BigDecimal highest_buy_bid;
	private BigDecimal lowest_sell_bid;
	private BigDecimal last_traded_price;
	private BigDecimal yes_price;
	private Volume volume;

	public BitbnsTicker(@JsonProperty("highest_buy_bid") BigDecimal highest_buy_bid,
			@JsonProperty("lowest_sell_bid") BigDecimal lowest_sell_bid,
			@JsonProperty("last_traded_price") BigDecimal last_traded_price,
			@JsonProperty("yes_price") BigDecimal yes_price, @JsonProperty("volume") Volume volume) {
		this.highest_buy_bid = highest_buy_bid;
		this.lowest_sell_bid = lowest_sell_bid;
		this.last_traded_price = last_traded_price;
		this.yes_price = yes_price;
		this.volume = volume;
	}

	public BigDecimal getHighest_buy_bid() {
		return highest_buy_bid;
	}

	public void setHighest_buy_bid(BigDecimal highest_buy_bid) {
		this.highest_buy_bid = highest_buy_bid;
	}

	public BigDecimal getLowest_sell_bid() {
		return lowest_sell_bid;
	}

	public void setLowest_sell_bid(BigDecimal lowest_sell_bid) {
		this.lowest_sell_bid = lowest_sell_bid;
	}

	public BigDecimal getLast_traded_price() {
		return last_traded_price;
	}

	public void setLast_traded_price(BigDecimal last_traded_price) {
		this.last_traded_price = last_traded_price;
	}

	public BigDecimal getYes_price() {
		return yes_price;
	}

	public void setYes_price(BigDecimal yes_price) {
		this.yes_price = yes_price;
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "BitbnsTicker [highest_buy_bid=" + highest_buy_bid + ", lowest_sell_bid=" + lowest_sell_bid
				+ ", last_traded_price=" + last_traded_price + ", yes_price=" + yes_price + ", volume=" + volume + "]";
	}

}


