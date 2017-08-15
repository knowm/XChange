package org.knowm.xchange.kraken.dto.marketdata;

import java.math.BigDecimal;

public class KrakenOHLC {

  private final long time;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final long count;

  public KrakenOHLC(long time, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal vwap, BigDecimal volume, long count) {
    this.time = time;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.vwap = vwap;
    this.volume = volume;
    this.count = count;
  }

  public long getTime() {
    return time;
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

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getVwap() {
    return vwap;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public long getCount() {
    return count;
  }

  @Override
  public String toString() {

    return "KrakenOHLC [time=" + time + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", vwap=" + vwap + ", volume=" + volume + ", count=" + count + "]";
  }

}
