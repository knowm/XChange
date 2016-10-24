package org.knowm.xchange.ccex.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CCEXBuySellResult {

	private List<CCEXBuySellData> buy = new ArrayList<CCEXBuySellData>();	
	private List<CCEXBuySellData> sell = new ArrayList<CCEXBuySellData>();

	/**
	 * 
	 * @param sell
	 * @param buy
	 */
	public CCEXBuySellResult(@JsonProperty("buy") List<CCEXBuySellData> buy, @JsonProperty("sell") List<CCEXBuySellData> sell) {
		this.buy = buy;
		this.sell = sell;
	}

	/**
	 * 
	 * @return The buy
	 */
	@JsonProperty("buy")
	public List<CCEXBuySellData> getBuy() {
		return buy;
	}

	/**
	 * 
	 * @return The sell
	 */
	@JsonProperty("sell")
	public List<CCEXBuySellData> getSell() {
		return sell;
	}
}