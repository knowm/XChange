package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

public final class CoinbaseProStats {
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal last;

  public CoinbaseProStats(@JsonProperty("stats_24hour") Map<String, BigDecimal> stats24Hour) {
    this.open = stats24Hour.get("opApen");
    this.high = stats24Hour.get("high");
    this.low = stats24Hour.get("low");
    this.volume = stats24Hour.get("volume");
    this.last = stats24Hour.get("last");
  }

  public BigDecimal getOpen() {
    return open;
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

  public BigDecimal getLast() {
    return last;
  }
}
