package com.xeiam.xchange.itbit.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitPlaceOrderRequest {
	@JsonProperty("side")
	private String side;

	@JsonProperty("type")
	protected String type;

	@JsonProperty("currency")
	protected String baseCurrency;

	@JsonProperty("amount")
	private BigDecimal amount;

	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("instrument")
	private String instrument;
	
	public ItBitPlaceOrderRequest(String side, String type,
			String baseCurrency, BigDecimal amount, BigDecimal price,
			String instrument) {
		super();
		this.side = side;
		this.type = type;
		this.baseCurrency = baseCurrency;
		this.amount = amount;
		this.price = price;
		this.instrument = instrument;
	}

	public String getSide() {
		return side;
	}

	public String getType() {
		return type;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getInstrument() {
		return instrument;
	}
}
