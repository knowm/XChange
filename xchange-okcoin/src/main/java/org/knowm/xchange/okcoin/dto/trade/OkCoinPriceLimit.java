package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkCoinPriceLimit {

  private final BigDecimal high;
  private final BigDecimal low;

  /**
   * Constructor
   *
   * @param high
   * @param low
   */
  public OkCoinPriceLimit(
      @JsonProperty("high") final BigDecimal high, @JsonProperty("low") final BigDecimal low) {

    this.high = high;
    this.low = low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  @Override
  public String toString() {

    return "OkCoinPriceLimit [high=" + high + ", low=" + low + "]";
  }
}
