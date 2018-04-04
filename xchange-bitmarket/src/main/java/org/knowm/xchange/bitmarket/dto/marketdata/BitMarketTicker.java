package org.knowm.xchange.bitmarket.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author kpysniak */
public final class BitMarketTicker {

  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal vwap;
  private final BigDecimal volume;

  /**
   * Constructor
   *
   * @param ask
   * @param bid
   * @param last
   * @param low
   * @param high
   * @param vwap
   * @param volume
   */
  public BitMarketTicker(
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("volume") BigDecimal volume) {

    this.ask = ask;
    this.bid = bid;
    this.last = last;
    this.low = low;
    this.high = high;
    this.vwap = vwap;
    this.volume = volume;
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

  public BigDecimal getVwap() {

    return vwap;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return "BitMarketTicker{"
        + "ask="
        + ask
        + ", bid="
        + bid
        + ", last="
        + last
        + ", low="
        + low
        + ", high="
        + high
        + ", vwap="
        + vwap
        + ", volume="
        + volume
        + '}';
  }
}
