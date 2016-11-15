package org.knowm.xchange.chbtc.dto.marketdata;

import java.math.BigDecimal;

public class ChbtcTicker {

  private BigDecimal last;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal vol;
  private BigDecimal buy;
  private BigDecimal sell;

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVol() {
    return vol;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getSell() {
    return sell;
  }

  @Override
  public String toString() {
    return String.format("ChbtcTicker{last=%s, high=%s, low=%s, vol=%s, buy=%s, sell=%s}", last, high, low, vol, buy, sell);
  }
}
