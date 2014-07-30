package com.xeiam.xchange.bitfinex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexLevel {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final float timestamp;

  /**
   * Constructor
   * 
   * @param price
   * @param amount
   * @param timestamp
   */
  public BitfinexLevel(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("timestamp") float timestamp) {

    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
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

  @Override
  public String toString() {

    return "BitfinexLevel [price=" + price + ", amount=" + amount + ", timestamp=" + timestamp + "]";
  }

}
