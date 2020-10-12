package org.knowm.xchange.wazirx.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WazirxTrade {

	private long id;
	private BigDecimal price;
	private BigDecimal volume;
	private BigDecimal funds;
	private String market;
	private Date created_at;
	private String side;
	 
	public WazirxTrade(@JsonProperty("id") long id, @JsonProperty("price") BigDecimal price,
			@JsonProperty("volume") BigDecimal volume, @JsonProperty("funds") BigDecimal funds, @JsonProperty("market") String market,
			@JsonProperty("created_at") Date created_at,@JsonProperty("side") String side) {
		this.id = id;
		this.price = price;
		this.volume = volume;
		this.funds = funds;
		this.market = market;
		this.created_at = created_at;
		this.side = side;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getFunds() {
		return funds;
	}

	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	@Override
	public String toString() {
		return "WazirxTrade [id=" + id + ", price=" + price + ", volume=" + volume + ", funds=" + funds + ", market="
				+ market + ", created_at=" + created_at + ", side=" + side + "]";
	}

}
