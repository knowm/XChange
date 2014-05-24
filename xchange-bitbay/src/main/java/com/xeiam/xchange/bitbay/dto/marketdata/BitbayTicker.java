package com.xeiam.xchange.bitbay.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author kpysniak
 */
public final class BitbayTicker {

  private final BigDecimal max;
  private final BigDecimal min;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal vwap;
  private final BigDecimal average;
  private final BigDecimal volume;

  /**
   * Constructor
   *
   * @param max
   * @param min
   * @param last
   * @param bid
   * @param ask
   * @param vwap
   * @param average
   * @param volume
   */
  public BitbayTicker(@JsonProperty("max") BigDecimal max, @JsonProperty("min") BigDecimal min,
                      @JsonProperty("last") BigDecimal last, @JsonProperty("bid")  BigDecimal bid,
                      @JsonProperty("ask") BigDecimal ask, @JsonProperty("vwap")  BigDecimal vwap,
                      @JsonProperty("average") BigDecimal average, @JsonProperty("volume") BigDecimal volume) {
    this.max = max;
    this.min = min;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.vwap = vwap;
    this.average = average;
    this.volume = volume;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getMin() {
    return min;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getVwap() {
    return vwap;
  }

  public BigDecimal getAverage() {
    return average;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "BitbayTicker{" +
        "max=" + max +
        ", min=" + min +
        ", last=" + last +
        ", bid=" + bid +
        ", ask=" + ask +
        ", vwap=" + vwap +
        ", average=" + average +
        ", volume=" + volume +
        '}';
  }
}
