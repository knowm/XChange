package org.knowm.xchange.wazirx.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WazirxTicker {
	
	private String base_unit;
	private String quote_unit;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal last;
	private String open;
	private BigDecimal volume;
	private BigDecimal sell;
	private BigDecimal buy;
	private String name;
	private String at;
	
	public WazirxTicker(
			   @JsonProperty("base_unit") String base_unit,
			   @JsonProperty("quote_unit") String quote_unit,
			   @JsonProperty("low") BigDecimal low,
			   @JsonProperty("high") BigDecimal high,
			   @JsonProperty("last") BigDecimal last,
			   @JsonProperty("open") String open,
			   @JsonProperty("volume") BigDecimal volume,
			   @JsonProperty("sell") BigDecimal sell,
			   @JsonProperty("buy") BigDecimal buy,
			   @JsonProperty("name") String name,
			   @JsonProperty("at") String at
			){
		this.base_unit = base_unit;
		this.quote_unit = quote_unit;
		this.low = low;
		this.high = high;
		this.last = last;
		this.open = open;
		this.volume = volume;
		this.sell = sell;
		this.buy = buy;
		this.name = name;
		this.at = at;
	}

	public String getBase_unit() {
		return base_unit;
	}

	public void setBase_unit(String base_unit) {
		this.base_unit = base_unit;
	}

	public String getQuote_unit() {
		return quote_unit;
	}

	public void setQuote_unit(String quote_unit) {
		this.quote_unit = quote_unit;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLast() {
		return last;
	}

	public void setLast(BigDecimal last) {
		this.last = last;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}
	
}
