package org.knowm.xchange.gateio.dto.marketdata;

import java.math.BigDecimal;

public class GateioKline {

  private final long id;
  private final BigDecimal open;
  private final BigDecimal close;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal vol;

  public GateioKline(
      long id, BigDecimal vol, BigDecimal close, BigDecimal high, BigDecimal low, BigDecimal open) {
    this.id = id;
    this.open = open;
    this.close = close;
    this.low = low;
    this.high = high;
    this.vol = vol;
  }

  @Override
  public String toString() {
    return String.format(
        "[id = %d, open = %f, close = %f, low = %f, high = %f, vol = %f]",
        getId(), getOpen(), getClose(), getLow(), getHigh(), getVol());
  }

  public long getId() {
    return id;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getVol() {
    return vol;
  }
}
