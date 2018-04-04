package org.knowm.xchange.hitbtc.v2.dto;

import java.math.BigDecimal;

public class HitbtcCandle {

  private String timestamp;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal min;

  private BigDecimal max;

  private BigDecimal volume;

  private BigDecimal volumeQuote;

  @Override
  public String toString() {
    return "KrakenOHLC [timestamp="
        + timestamp
        + ", open="
        + open
        + ", max="
        + max
        + ", min="
        + min
        + ", close="
        + close
        + ", volumeQuote="
        + volumeQuote
        + ", volume="
        + volume
        + "]";
  }

  public String getTimestamp() {
    return timestamp;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getMin() {
    return min;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVolumeQuote() {
    return volumeQuote;
  }
}
