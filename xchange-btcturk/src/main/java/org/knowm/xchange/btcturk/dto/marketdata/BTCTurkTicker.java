package org.knowm.xchange.btcturk.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.btcturk.BTCTurk;

/** Created by semihunaldi on 26/11/2017 */
public final class BTCTurkTicker {

  private final BigDecimal high;
  private final BigDecimal last;
  private final long timestamp;
  private final BigDecimal bid;
  private final BigDecimal volume;
  private final BigDecimal low;
  private final BigDecimal ask;
  private final BigDecimal open;
  private final BigDecimal average;
  private BTCTurk.Pair pair;

  public BTCTurkTicker(
      @JsonProperty("pair") BTCTurk.Pair pair,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("average") BigDecimal average) {
    this.pair = pair;
    this.high = high;
    this.last = last;
    this.timestamp = timestamp;
    this.bid = bid;
    this.volume = volume;
    this.low = low;
    this.ask = ask;
    this.open = open;
    this.average = average;
  }

  public BTCTurk.Pair getPair() {
    return pair;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLast() {
    return last;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getAverage() {
    return average;
  }

  @Override
  public String toString() {
    return "BTCTurkTicker {"
        + "pair="
        + pair
        + ", high="
        + high
        + ", last="
        + last
        + ", timestamp="
        + timestamp
        + ", bid="
        + bid
        + ", volume="
        + volume
        + ", low="
        + low
        + ", ask="
        + ask
        + ", open="
        + open
        + ", average="
        + average
        + '}';
  }
}
