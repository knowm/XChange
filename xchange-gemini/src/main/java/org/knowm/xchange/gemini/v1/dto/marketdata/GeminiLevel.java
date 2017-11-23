package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiLevel {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal timestamp;

  /**
   * Constructor
   *
   * @param price
   * @param amount
   * @param timestamp
   */
  public GeminiLevel(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("timestamp") BigDecimal timestamp) {

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

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "GeminiLevel [price=" + price + ", amount=" + amount + ", timestamp=" + timestamp + "]";
  }

}
