package com.xeiam.xchange.btcchina.dto.marketdata;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTickerObject implements Serializable {

  private static final long serialVersionUID = 2014080901L;

  private final BigDecimal buy;
  private final BigDecimal high;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal sell;
  private final BigDecimal vol;
  private final long date;

  /**
   * Constructor
   * 
   * @param buy
   * @param sell
   * @param high
   * @param low
   * @param vol
   * @param last
   */
  public BTCChinaTickerObject(@JsonProperty("buy") BigDecimal buy, @JsonProperty("sell") BigDecimal sell, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
      @JsonProperty("vol") BigDecimal vol, @JsonProperty("last") BigDecimal last, @JsonProperty("date") long date) {

    this.high = high;
    this.low = low;
    this.vol = vol;
    this.last = last;
    this.buy = buy;
    this.sell = sell;
    this.date = date;
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

  public BigDecimal getVol() {

    return this.vol;
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return "BTCChinaTicker [last=" + last + ", high=" + high + ", low=" + low + ", buy=" + buy + ", sell=" + sell + ", vol=" + vol + ", date=" + date + "]";

  }
}
