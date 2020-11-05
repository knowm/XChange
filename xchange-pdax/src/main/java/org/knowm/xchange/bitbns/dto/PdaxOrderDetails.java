package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxOrderDetails {

	private long orderId;
	private String orderType;
	private String tradeCurrency;
	private BigDecimal amount;
	private String side;
	private String settlementCurrency;
	private String orderStatus;
	private BigDecimal outstandingTradedCurrencyAmount;
	private BigDecimal limitPrice;
	private List<PdaxOrderTrades> trades;
	private long timestamp;

	public PdaxOrderDetails(@JsonProperty("order_id") long orderId, @JsonProperty("order_type") String orderType,
			@JsonProperty("traded_currency") String tradeCurrency, @JsonProperty("amount") BigDecimal amount,
			@JsonProperty("side") String sell, @JsonProperty("settlement_currency") String settlementCurrency,
			@JsonProperty("order_status") String orderStatus,
			@JsonProperty("outstanding_traded_currency_amount") BigDecimal outstandingTradedCurrencyAmount,
			@JsonProperty("limit_price") BigDecimal limitPrice, @JsonProperty("trades") List<PdaxOrderTrades> trades,
			@JsonProperty("timestamp") long timestamp) {

		this.orderId = orderId;
		this.orderType = orderType;
		this.tradeCurrency = tradeCurrency;
		this.amount = amount;
		this.side = sell;
		this.settlementCurrency = settlementCurrency;
		this.orderStatus = orderStatus;
		this.outstandingTradedCurrencyAmount = outstandingTradedCurrencyAmount;
		this.limitPrice = limitPrice;
		this.trades = trades;
		this.timestamp = timestamp;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTradeCurrency() {
		return tradeCurrency;
	}

	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOutstandingTradedCurrencyAmount() {
		return outstandingTradedCurrencyAmount;
	}

	public void setOutstandingTradedCurrencyAmount(BigDecimal outstandingTradedCurrencyAmount) {
		this.outstandingTradedCurrencyAmount = outstandingTradedCurrencyAmount;
	}

	public BigDecimal getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}

	public List<PdaxOrderTrades> getTrades() {
		return trades;
	}

	public void setTrades(List<PdaxOrderTrades> trades) {
		this.trades = trades;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "PdaxOrderDetails [orderId=" + orderId + ", orderType=" + orderType + ", tradeCurrency=" + tradeCurrency
				+ ", amount=" + amount + ", sell=" + side + ", settlementCurrency=" + settlementCurrency
				+ ", orderStatus=" + orderStatus + ", outstandingTradedCurrencyAmount="
				+ outstandingTradedCurrencyAmount + ", limitPrice=" + limitPrice + ", trades=" + trades + ", timestamp="
				+ timestamp + "]";
	}

}
