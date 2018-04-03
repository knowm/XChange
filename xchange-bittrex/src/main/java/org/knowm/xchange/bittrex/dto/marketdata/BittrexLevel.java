package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexLevel {

  private final BigDecimal rate;
  private final BigDecimal quantity;

  /**
   * Constructor
   *
   * @param rate
   * @param quantity
   */
  public BittrexLevel(
      @JsonProperty("Rate") BigDecimal rate, @JsonProperty("Quantity") BigDecimal quantity) {

    this.rate = rate;
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {

    return rate;
  }

  public BigDecimal getAmount() {

    return quantity;
  }

  @Override
  public String toString() {

    return "BittrexLevel [rate=" + rate + ", quantity=" + quantity + "]";
  }
}
