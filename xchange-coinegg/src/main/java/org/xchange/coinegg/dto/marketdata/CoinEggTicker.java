package org.xchange.coinegg.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinEggTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal last;
  private final BigDecimal volume;

  public CoinEggTicker(
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("vol") BigDecimal volume) {

    this.high = high;
    this.low = low;
    this.buy = buy;
    this.sell = sell;
    this.last = last;
    this.volume = volume;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getSell() {
    return sell;
  }

  @Override
  public String toString() {
    return "";
  }
}
