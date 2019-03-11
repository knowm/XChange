package org.knowm.xchange.coinfloor.dto.markedata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinfloorTicker {
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;

  public CoinfloorTicker(
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask) {

    this.last = last;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getVwap() {
    return vwap;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  @Override
  public String toString() {
    return "CoinfloorTicker [last="
        + last
        + ", high="
        + high
        + ", low="
        + low
        + ", vwap="
        + vwap
        + ", volume="
        + volume
        + ", bid="
        + bid
        + ", ask="
        + ask
        + "]";
  }
}
