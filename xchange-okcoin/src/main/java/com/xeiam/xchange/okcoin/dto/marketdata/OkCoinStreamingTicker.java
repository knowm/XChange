package com.xeiam.xchange.okcoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinStreamingTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal last;
  private final String vol;
  private final long timestamp;

  public OkCoinStreamingTicker(@JsonProperty("high") final BigDecimal high, @JsonProperty("low") final BigDecimal low, @JsonProperty("buy") final BigDecimal buy, @JsonProperty("sell") final BigDecimal sell,
      @JsonProperty("last") final BigDecimal last, @JsonProperty("vol") final String vol, @JsonProperty("timestamp") long timestamp) {

    this.high = high;
    this.low = low;
    this.buy = buy;
    this.sell = sell;
    this.last = last;
    this.vol = vol;
    this.timestamp = timestamp;
  }

  /**
   * @return the high
   */
  public BigDecimal getHigh() {

    return high;
  }

  /**
   * @return the low
   */
  public BigDecimal getLow() {

    return low;
  }

  /**
   * @return the buy
   */
  public BigDecimal getBuy() {

    return buy;
  }

  /**
   * @return the sell
   */
  public BigDecimal getSell() {

    return sell;
  }

  /**
   * @return the last
   */
  public BigDecimal getLast() {

    return last;
  }

  /**
   * @return the vol
   */
  public String getVol() {

    return vol;
  }
  
  /**
   * @return the timestamp
   */  
  public long getTimestamp() {
    return timestamp;
  }

}
