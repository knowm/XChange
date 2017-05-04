package org.knowm.xchange.livecoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LivecoinTicker {
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal vwap;
  private final BigDecimal maxBid;
  private final BigDecimal minAsk;
  private final BigDecimal bestBid;
  private final BigDecimal bestAsk;

  public LivecoinTicker(@JsonProperty("last") BigDecimal last, @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("max_bid") BigDecimal maxBid,
      @JsonProperty("min_ask") BigDecimal minAsk, @JsonProperty("best_bid") BigDecimal bestBid,
      @JsonProperty("best_ask") BigDecimal bestAsk) {

    this.last = last;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.vwap = vwap;
    this.maxBid = maxBid;
    this.minAsk = minAsk;
    this.bestBid = bestBid;
    this.bestAsk = bestAsk;

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

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getVwap() {
    return vwap;
  }

  public BigDecimal getBestAsk() {
    return bestAsk;
  }

  public BigDecimal getMinAsk() {
    return minAsk;
  }

  public BigDecimal getMaxBid() {
    return maxBid;
  }

  public BigDecimal getBestBid() {
    return bestBid;
  }

  @Override
  public String toString() {
    return "YoBitTicker [last=" + last + ", high=" + high + ", low=" + low + ", volume=" + volume + ", vwap=" + vwap
        + ", maxBid=" + maxBid + ", minAsk=" + minAsk + ", bestBid=" + bestBid + ", bestAsk=" + bestAsk + "]";
  }

}
