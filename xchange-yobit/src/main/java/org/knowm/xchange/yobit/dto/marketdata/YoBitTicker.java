package org.knowm.xchange.yobit.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YoBitTicker {
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal avg;
  private final BigDecimal vol;
  private final BigDecimal volCur;
  private final BigDecimal last;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final long updated;

  public YoBitTicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
      @JsonProperty("avg") BigDecimal avg, @JsonProperty("vol") BigDecimal vol,
      @JsonProperty("vol_cur") BigDecimal volCur, @JsonProperty("last") BigDecimal last,
      @JsonProperty("buy") BigDecimal buy, @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("updated") long updated) {
    this.high = high;
    this.low = low;
    this.avg = avg;
    this.vol = vol;
    this.volCur = volCur;
    this.last = last;
    this.buy = buy;
    this.sell = sell;
    this.updated = updated;
  }

  public BigDecimal getBuy() {
    return buy;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getVol() {
    return vol;
  }

  public BigDecimal getVolCur() {
    return volCur;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getSell() {
    return sell;
  }

  public long getUpdated() {
    return updated;
  }

  @Override
  public String toString() {
    return "YoBitTickker [buy=" + buy + ", high=" + high + ", low=" + low + ", avg=" + avg + ", vol=" + vol + ", volCur="
        + volCur + ", last=" + last + ", sell=" + sell + ", updated=" + updated + "]";
  }

}
