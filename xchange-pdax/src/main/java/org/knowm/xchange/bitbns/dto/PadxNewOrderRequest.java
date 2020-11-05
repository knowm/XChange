package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PadxNewOrderRequest {

	@JsonProperty("order_type")
	private String order_type;

	@JsonProperty("side")
	private String side;

	@JsonProperty("traded_currency")
	private String traded_currency;

	@JsonProperty("settlement_currency")
	private String settlement_currency;

	@JsonProperty("amount")
	private BigDecimal amount;

	@JsonProperty("limit_price")
	private BigDecimal limit_price;

	@JsonProperty("client_id")
	private String client_id;

	@JsonProperty("immediate_or_cancel")
	private boolean immediate_or_cancel;
	
	public PadxNewOrderRequest(String order_type,
			String side,String traded_currency,
			String settlement_currency,
			BigDecimal amount,BigDecimal limit_price,
			String client_id,boolean immediate_or_cancel) {
	
		this.order_type=order_type;
		this.side=side;
		this.traded_currency=traded_currency;
		this.settlement_currency=settlement_currency;
		this.amount=amount;
		this.limit_price=limit_price;
		this.client_id=client_id;
		this.immediate_or_cancel=immediate_or_cancel;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getTraded_currency() {
		return traded_currency;
	}

	public void setTraded_currency(String traded_currency) {
		this.traded_currency = traded_currency;
	}

	public String getSettlement_currency() {
		return settlement_currency;
	}

	public void setSettlement_currency(String settlement_currency) {
		this.settlement_currency = settlement_currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getLimit_price() {
		return limit_price;
	}

	public void setLimit_price(BigDecimal limit_price) {
		this.limit_price = limit_price;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public boolean isImmediate_or_cancel() {
		return immediate_or_cancel;
	}

	public void setImmediate_or_cancel(boolean immediate_or_cancel) {
		this.immediate_or_cancel = immediate_or_cancel;
	}

	@Override
	public String toString() {
		return "PadxNewOrder [order_type=" + order_type + ", side=" + side + ", traded_currency=" + traded_currency
				+ ", settlement_currency=" + settlement_currency + ", amount=" + amount + ", limit_price=" + limit_price
				+ ", client_id=" + client_id + ", immediate_or_cancel=" + immediate_or_cancel + "]";
	}

}
