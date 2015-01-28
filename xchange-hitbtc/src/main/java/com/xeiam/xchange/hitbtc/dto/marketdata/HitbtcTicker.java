package com.xeiam.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public final class HitbtcTicker {

  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volume;
  private final long timetamp;

  /**
   * Constructor
   * 
   * @param ask
   * @param bid
   * @param last
   * @param low
   * @param high
   * @param volume
   * @param timetamp
   */
  public HitbtcTicker(@JsonProperty("ask") BigDecimal ask, @JsonProperty("bid") BigDecimal bid, @JsonProperty("last") BigDecimal last,
      @JsonProperty("low") BigDecimal low, @JsonProperty("high") BigDecimal high, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("timestamp") long timetamp) {

    this.ask = ask;
    this.bid = bid;
    this.last = last;
    this.low = low;
    this.high = high;
    this.volume = volume;
    this.timetamp = timetamp;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public long getTimetamp() {

    return timetamp;
  }

  @Override
  public String toString() {

    return "HitbtcTicker{" + "ask=" + ask + ", bid=" + bid + ", last=" + last + ", low=" + low + ", high=" + high + ", volume=" + volume
        + ", timetamp=" + timetamp + '}';
  }
}
