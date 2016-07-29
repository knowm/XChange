package org.knowm.xchange.loyalbit.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoyalbitTicker {

  public final BigDecimal last;
  public final BigDecimal ask;
  public final BigDecimal bid;
  public final BigDecimal high;
  public final BigDecimal low;
  public final BigDecimal volume;

  public LoyalbitTicker(@JsonProperty("last") BigDecimal last, @JsonProperty("ask") BigDecimal ask, @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume) {
    this.last = last;
    this.ask = ask;
    this.bid = bid;
    this.high = high;
    this.low = low;
    this.volume = volume;
  }

  @Override
  public String toString() {
    return String.format("LoyalbitTicker{last=%s, ask=%s, bid=%s, high=%s, low=%s, volume=%s}", last, ask, bid, high, low, volume);
  }
}
