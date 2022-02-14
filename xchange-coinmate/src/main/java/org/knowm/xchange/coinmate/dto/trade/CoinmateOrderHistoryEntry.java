package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateOrderHistoryEntry {

  private final long id;
  private final long timestamp;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal remainingAmount;
  private final BigDecimal originalAmount;
  private final String status;
  private final String orderTradeType;
  private final BigDecimal stopPrice;
  private final boolean trailing;
  private final Long trailingUpdatedTimestamp;
  private final BigDecimal originalStopPrice;
  private final BigDecimal marketPriceAtLastUpdate;
  private final BigDecimal marketPriceAtOrderCreation;
  private final boolean hidden;
  private final BigDecimal avgPrice;
  private final String stopLossOrderId;
  private final String originalOrderId;

  public CoinmateOrderHistoryEntry(
      @JsonProperty("id") long id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("type") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("remainingAmount") BigDecimal remainingAmount,
      @JsonProperty("originalAmount") BigDecimal originalAmount,
      @JsonProperty("status") String status,
      @JsonProperty("orderTradeType") String orderTradeType,
      @JsonProperty("stopPrice") BigDecimal stopPrice,
      @JsonProperty("trailing") boolean trailing,
      @JsonProperty("trailingUpdatedTimestamp") Long trailingUpdatedTimestamp,
      @JsonProperty("originalStopPrice") BigDecimal originalStopPrice,
      @JsonProperty("marketPriceAtLastUpdate") BigDecimal marketPriceAtLastUpdate,
      @JsonProperty("marketPriceAtOrderCreation") BigDecimal marketPriceAtOrderCreation,
      @JsonProperty("hidden") boolean hidden,
      @JsonProperty("avgPrice") BigDecimal avgPrice,
      @JsonProperty("stopLossOrderId") String stopLossOrderId,
      @JsonProperty("originalOrderId") String originalOrderId) {

    this.id = id;
    this.timestamp = timestamp;
    this.type = type;
    this.price = price;
    this.remainingAmount = remainingAmount;
    this.originalAmount = originalAmount;
    this.status = status;
    this.orderTradeType = orderTradeType;
    this.stopPrice = stopPrice;
    this.trailing = trailing;
    this.trailingUpdatedTimestamp = trailingUpdatedTimestamp;
    this.originalStopPrice = originalStopPrice;
    this.marketPriceAtLastUpdate = marketPriceAtLastUpdate;
    this.marketPriceAtOrderCreation = marketPriceAtOrderCreation;
    this.hidden = hidden;
    this.avgPrice = avgPrice;
    this.stopLossOrderId = stopLossOrderId;
    this.originalOrderId = originalOrderId;
  }

  public long getId() {
    return id;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getRemainingAmount() {
    return remainingAmount;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public boolean isTrailing() {
    return trailing;
  }

  public Long getTrailingUpdatedTimestamp() {
    return trailingUpdatedTimestamp;
  }

  public BigDecimal getOriginalStopPrice() {
    return originalStopPrice;
  }

  public BigDecimal getMarketPriceAtLastUpdate() {
    return marketPriceAtLastUpdate;
  }

  public BigDecimal getMarketPriceAtOrderCreation() {
    return marketPriceAtOrderCreation;
  }

  public boolean isHidden() {
    return hidden;
  }

  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public String getStopLossOrderId() {
    return stopLossOrderId;
  }

  public String getOriginalOrderId() {
    return originalOrderId;
  }

  public String getOrderTradeType() {
    return orderTradeType;
  }
}
