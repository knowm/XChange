package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PdaxOrderTrades {

	private UUID tradeId;
	private long timestamp;
	private BigDecimal tradedCurrencyFillAmount;
	private BigDecimal settlementCurrencyFillAmount;
	private BigDecimal settlementCurrencyFillAmountUnrounded;
	private BigDecimal price;
	private String side;
	
	public PdaxOrderTrades(@JsonProperty("trade_id") UUID tradeId,
			@JsonProperty("timestamp") long timestamp,
			@JsonProperty("traded_currency_fill_amount") BigDecimal tradedCurrencyFillAmount,
			@JsonProperty("settlement_currency_fill_amount") BigDecimal settlementCurrencyFillAmount,
			@JsonProperty("settlement_currency_fill_amount_unrounded") BigDecimal settlementCurrencyFillAmountUnrounded,
			@JsonProperty("price") BigDecimal price,
			@JsonProperty("side") String side
			) {
		 this.tradeId=tradeId;
		 this.timestamp=timestamp;
		 this.tradedCurrencyFillAmount=tradedCurrencyFillAmount;
		 this.settlementCurrencyFillAmount=settlementCurrencyFillAmount;
		 this.settlementCurrencyFillAmountUnrounded=settlementCurrencyFillAmountUnrounded;
		 this.price=price;
		 this.side=side;
	}

	public UUID getTradeId() {
		return tradeId;
	}

	public void setTradeId(UUID tradeId) {
		this.tradeId = tradeId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getTradedCurrencyFillAmount() {
		return tradedCurrencyFillAmount;
	}

	public void setTradedCurrencyFillAmount(BigDecimal tradedCurrencyFillAmount) {
		this.tradedCurrencyFillAmount = tradedCurrencyFillAmount;
	}

	public BigDecimal getSettlementCurrencyFillAmount() {
		return settlementCurrencyFillAmount;
	}

	public void setSettlementCurrencyFillAmount(BigDecimal settlementCurrencyFillAmount) {
		this.settlementCurrencyFillAmount = settlementCurrencyFillAmount;
	}

	public BigDecimal getSettlementCurrencyFillAmountUnrounded() {
		return settlementCurrencyFillAmountUnrounded;
	}

	public void setSettlementCurrencyFillAmountUnrounded(BigDecimal settlementCurrencyFillAmountUnrounded) {
		this.settlementCurrencyFillAmountUnrounded = settlementCurrencyFillAmountUnrounded;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	@Override
	public String toString() {
		return "PdaxOrderTrades [tradeId=" + tradeId + ", timestamp=" + timestamp + ", tradedCurrencyFillAmount="
				+ tradedCurrencyFillAmount + ", settlementCurrencyFillAmount=" + settlementCurrencyFillAmount
				+ ", settlementCurrencyFillAmountUnrounded=" + settlementCurrencyFillAmountUnrounded + ", price="
				+ price + ", side=" + side + "]";
	}

}
