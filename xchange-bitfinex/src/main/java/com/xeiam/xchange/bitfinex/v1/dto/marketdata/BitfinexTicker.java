package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexTicker {

  private final BigDecimal mid;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal last_price;
  private final float timestamp;

  public BitfinexTicker(@JsonProperty("mid") BigDecimal mid, @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask, @JsonProperty("last_price") BigDecimal last_price,
      @JsonProperty("timestamp") float timestamp) {

    this.mid = mid;
    this.bid = bid;
    this.ask = ask;
    this.last_price = last_price;
    this.timestamp = timestamp;
  }

  public BigDecimal getMid() {

    return mid;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getLast_price() {

    return last_price;
  }

  public float getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("BitfinexTicker [mid=");
    builder.append(mid);
    builder.append(", bid=");
    builder.append(bid);
    builder.append(", ask=");
    builder.append(ask);
    builder.append(", last_price=");
    builder.append(last_price);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append("]");
    return builder.toString();
  }
}
