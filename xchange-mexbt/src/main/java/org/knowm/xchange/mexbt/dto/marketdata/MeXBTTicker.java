package org.knowm.xchange.mexbt.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final long bidCount;
  private final long askCount;
  private final BigDecimal change24Hour;
  private final BigDecimal volume24Hour;
  private final long trades24Hour;

  public MeXBTTicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("last") BigDecimal last,
      @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask, @JsonProperty("bidCount") long bidCount,
      @JsonProperty("askCount") long askCount, @JsonProperty("change24Hour") BigDecimal change24Hour,
      @JsonProperty("volume24Hour") BigDecimal volume24Hour, @JsonProperty("trades24Hour") long trades24Hour) {
    this.high = high;
    this.low = low;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.bidCount = bidCount;
    this.askCount = askCount;
    this.change24Hour = change24Hour;
    this.volume24Hour = volume24Hour;
    this.trades24Hour = trades24Hour;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
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

  public long getBidCount() {
    return bidCount;
  }

  public long getAskCount() {
    return askCount;
  }

  public BigDecimal getChange24Hour() {
    return change24Hour;
  }

  public BigDecimal getVolume24Hour() {
    return volume24Hour;
  }

  public long getTrades24Hour() {
    return trades24Hour;
  }

}
