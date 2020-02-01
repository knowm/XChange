package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateTradeHistoryEntry {

  private final long transactionId;
  private final long createdTimestamp;
  private final String currencyPair;
  private final String type;
  private final String orderType;
  private final long orderId;
  private final BigDecimal amount;
  private final BigDecimal price;
  private final BigDecimal fee;
  private final String feeType;

  public CoinmateTradeHistoryEntry(
      @JsonProperty("transactionId") long transactionId,
      @JsonProperty("createdTimestamp") long createdTimestamp,
      @JsonProperty("currencyPair") String currencyPair,
      @JsonProperty("orderType") String orderType,
      @JsonProperty("type") String type,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("feeType") String feeType) {

    this.transactionId = transactionId;
    this.createdTimestamp = createdTimestamp;
    this.currencyPair = currencyPair;
    this.type = type;
    this.orderType = orderType;
    this.orderId = orderId;
    this.amount = amount;
    this.price = price;
    this.fee = fee;
    this.feeType = feeType;
  }

  public long getTransactionId() {
    return transactionId;
  }

  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public String getCurrencyPair() {
    return currencyPair;
  }

  public String getType() {
    return type;
  }

  public String getOrderType() {
    return orderType;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getFeeType() {
    return feeType;
  }
}
