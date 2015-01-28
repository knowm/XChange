package com.xeiam.xchange.btce.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from BTCE
 */
@Deprecated
public final class BTCETicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal avg;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final long serverTime;
  private final BigDecimal vol;
  private final BigDecimal volCur;

  /**
   * Constructor
   * 
   * @param high
   * @param low
   * @param vol
   * @param last
   * @param avg
   * @param buy
   * @param serverTime
   * @param volCur
   * @param sell
   */
  public BTCETicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("vol") BigDecimal vol,
      @JsonProperty("last") BigDecimal last, @JsonProperty("avg") BigDecimal avg, @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("server_time") long serverTime, @JsonProperty("vol_cur") BigDecimal volCur, @JsonProperty("sell") BigDecimal sell) {

    this.high = high;
    this.low = low;
    this.last = last;
    this.avg = avg;
    this.buy = buy;
    this.sell = sell;
    this.serverTime = serverTime;
    this.vol = vol;
    this.volCur = volCur;
  }

  public BigDecimal getAvg() {

    return avg;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getSell() {

    return sell;
  }

  public long getServerTime() {

    return serverTime;
  }

  public BigDecimal getVol() {

    return vol;
  }

  public BigDecimal getVolCur() {

    return volCur;
  }

  @Override
  public String toString() {

    return "BTCETickerObject [last=" + last + ", high=" + high + ", low=" + low + ", avg=" + avg + ", buy=" + buy + ", sell=" + sell
        + ", serverTime=" + serverTime + ", vol=" + vol + ", volCur=" + volCur + "]";
  }

}
