package org.knowm.xchange.liqui.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class LiquiTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal avg;
  private final BigDecimal vol;
  private final BigDecimal volCur;
  private final BigDecimal last;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final long updated;

  public LiquiTicker(
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("avg") BigDecimal avg,
      @JsonProperty("vol") BigDecimal vol,
      @JsonProperty("vol_cur") BigDecimal volCur,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell,
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

  public BigDecimal getHigh() {
    return this.high;
  }

  public BigDecimal getLow() {
    return this.low;
  }

  public BigDecimal getAvg() {
    return this.avg;
  }

  public BigDecimal getVol() {
    return this.vol;
  }

  public BigDecimal getVolCur() {
    return this.volCur;
  }

  public BigDecimal getLast() {
    return this.last;
  }

  public BigDecimal getBuy() {
    return this.buy;
  }

  public BigDecimal getSell() {
    return this.sell;
  }

  public long getUpdated() {
    return this.updated;
  }

  @Override
  public String toString() {
    return "LiquiTicker{"
        + "high="
        + this.high
        + ", low="
        + this.low
        + ", avg="
        + this.avg
        + ", vol="
        + this.vol
        + ", volCur="
        + this.volCur
        + ", last="
        + this.last
        + ", buy="
        + this.buy
        + ", sell="
        + this.sell
        + ", updated="
        + this.updated
        + '}';
  }
}
