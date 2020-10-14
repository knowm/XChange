package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;

public class BitbnsNewOrderRequest {

	private String symbol;
	private String side;
	private String quantity;
	private String rate;
	// private BigDecimal target_rate;
	// private BigDecimal t_rate;
	// private BigDecimal trail_rate;

	public BitbnsNewOrderRequest(String side, String symbol, String quantity, String rate) {
		this.side = side;
		this.symbol = symbol;
		this.quantity = quantity;
		this.rate = rate;
		// this.target_rate = target_rate;
		// this.t_rate = t_rate;
		// this.trail_rate = trail_rate;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "BitbnsNewOrderRequest [side=" + side + ", symbol=" + symbol + ", quantity=" + quantity + ", rate="
				+ rate + "]";
	}

}
