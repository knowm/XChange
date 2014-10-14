package com.xeiam.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexTradeResponse {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final float timestamp;
  private final String exchange;
  private final String type;
  private final String tradeId;
  private final String orderId;

  /**
   * Constructor
   *
   * @param price
   * @param amount
   * @param timestamp
   * @param exchange
   * @param type
   * @param tradeId
   * @param orderId
   */
  public BitfinexTradeResponse(@JsonProperty("price") final BigDecimal price, @JsonProperty("amount") final BigDecimal amount, @JsonProperty("timestamp") final float timestamp,
      @JsonProperty("exchange") final String exchange, @JsonProperty("type") final String type, @JsonProperty("tid") final String tradeId, @JsonProperty("order_id") final String orderId) {

    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
    this.exchange = exchange;
    this.type = type;
    this.tradeId = tradeId;
    this.orderId = orderId;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public float getTimestamp() {

    return timestamp;
  }

  public String getType() {

    return type;
  }

  public String getOrderId() {

    return orderId;
  }

  public String getTradeId() {

    return tradeId;
  }

  @Override
  public String toString() {

    final StringBuilder builder = new StringBuilder();
    builder.append("BitfinexTradeResponse [price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", exchange=");
    builder.append(exchange);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    builder.append(", tradeId=");
    builder.append(tradeId);
    builder.append("]");
    builder.append(", orderId=");
    builder.append(orderId);
    builder.append("]");
    return builder.toString();
  }
}
