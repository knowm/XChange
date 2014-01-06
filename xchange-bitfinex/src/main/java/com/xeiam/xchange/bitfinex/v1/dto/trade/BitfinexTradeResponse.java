package com.xeiam.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexTradeResponse {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final float timestamp;
  private final String exchange;
  private final String type;

  public BitfinexTradeResponse(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") float timestamp,
      @JsonProperty("exchange") String exchange, @JsonProperty("type") String type) {

    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
    this.exchange = exchange;
    this.type = type;
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

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
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
    return builder.toString();
  }
}
