package org.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitZTrades {

  private final BigDecimal min;
  private final BigDecimal max;
  private final BigDecimal sum;

  private final BitZPublicTrade[] trades;

  public BitZTrades(
      @JsonProperty("min") BigDecimal min,
      @JsonProperty("max") BigDecimal max,
      @JsonProperty("sum") BigDecimal sum,
      @JsonProperty("d") BitZPublicTrade[] trades) {

    this.min = min;
    this.max = max;
    this.sum = sum;
    this.trades = trades;
  }

  public BigDecimal getMin() {
    return min;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getSum() {
    return sum;
  }

  public BitZPublicTrade[] getTrades() {
    return trades;
  }
}
