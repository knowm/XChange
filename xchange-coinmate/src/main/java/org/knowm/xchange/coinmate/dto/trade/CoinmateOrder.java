package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateOrder {

  private final long id;
  private final long timestamp;
  private final String type;
  private final BigDecimal price;
  private final BigDecimal remainingAmount;
  private final BigDecimal originalAmount;
  private final BigDecimal stopPrice;
  private final String status;
  private final String orderTradeType;
  private final BigDecimal avgPrice;
  private final String stopLossOrderId;
  private final String originalOrderId;

  public CoinmateOrder(
      @JsonProperty("id") long id,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("type") String type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("remainingAmount") BigDecimal remainingAmount,
      @JsonProperty("originalAmount") BigDecimal originalAmount,
      @JsonProperty("stopPrice") BigDecimal stopPrice,
      @JsonProperty("status") String status,
      @JsonProperty("orderTradeType") String orderTradeType,
      @JsonProperty("avgPrice") BigDecimal avgPrice,
      @JsonProperty("stopLossOrderId") String stopLossOrderId,
      @JsonProperty("originalOrderId") String originalOrderId) {

    this.id = id;
    this.timestamp = timestamp;
    this.type = type;
    this.price = price;
    this.remainingAmount = remainingAmount;
    this.originalAmount = originalAmount;
    this.stopPrice = stopPrice;
    this.status = status;
    this.orderTradeType = orderTradeType;
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

  public String getOrderTradeType() {
    return orderTradeType;
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

  @Override
  public String toString() {
    return "CoinmateOrder{"
        + "id="
        + id
        + ", timestamp="
        + timestamp
        + ", type='"
        + type
        + '\''
        + ", price="
        + price
        + ", remainingAmount="
        + remainingAmount
        + ", originalAmount="
        + originalAmount
        + ", stopPrice="
        + stopPrice
        + ", status='"
        + status
        + '\''
        + ", orderTradeType='"
        + orderTradeType
        + '\''
        + ", avgPrice="
        + avgPrice
        + ", stopLossOrderId="
        + stopLossOrderId
        + ", originalOrderId="
        + originalOrderId
        + '}';
  }
}
