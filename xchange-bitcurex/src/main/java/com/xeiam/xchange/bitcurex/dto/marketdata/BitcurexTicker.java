package com.xeiam.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexTicker {

  private final BigDecimal avg;
  private final BigDecimal buy;
  private final BigDecimal high;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal sell;
  private final BigDecimal time;
  private final BigDecimal vol;
  private final BigDecimal vwap;

  /**
   * @param high
   * @param low
   * @param vol
   * @param last
   * @param avg
   * @param buy
   * @param sell
   * @param vwap
   * @param time
   */
  public BitcurexTicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("vol") BigDecimal vol, @JsonProperty("last") BigDecimal last,
      @JsonProperty("avg") BigDecimal avg, @JsonProperty("buy") BigDecimal buy, @JsonProperty("sell") BigDecimal sell, @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("time") BigDecimal time) {

    this.high = high;
    this.low = low;
    this.vol = vol;
    this.last = last;
    this.time = time;
    this.sell = sell;
    this.buy = buy;
    this.vwap = vwap;
    this.avg = avg;
  }

  public BigDecimal getAvg() {

    return this.avg;
  }

  public BigDecimal getBuy() {

    return this.buy;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getSell() {

    return this.sell;
  }

  public BigDecimal getTime() {

    return this.time;
  }

  public BigDecimal getVol() {

    return this.vol;
  }

  public BigDecimal getVwap() {

    return this.vwap;
  }

  @Override
  public String toString() {

    return "BitcurexTicker [avg=" + avg + ", buy=" + buy + ", high=" + high + ", last=" + last + ", low=" + low + ", sell=" + sell + ", time=" + time + ", vol=" + vol + ", vwap=" + vwap + "]";
  }
}
