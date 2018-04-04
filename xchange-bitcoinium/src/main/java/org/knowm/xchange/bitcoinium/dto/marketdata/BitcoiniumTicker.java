package org.knowm.xchange.bitcoinium.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Data object representing Ticker from Bitcoinium Web Service */
public final class BitcoiniumTicker {

  private final BigDecimal last;
  private final long timestamp;
  private final BigDecimal volume;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal trades;

  /**
   * Constructor
   *
   * @param last
   * @param timestamp
   * @param volume
   * @param high
   * @param low
   * @param bid
   * @param ask
   */
  public BitcoiniumTicker(
      @JsonProperty("l") BigDecimal last,
      @JsonProperty("t") long timestamp,
      @JsonProperty("v") BigDecimal volume,
      @JsonProperty("h") BigDecimal high,
      @JsonProperty("lo") BigDecimal low,
      @JsonProperty("b") BigDecimal bid,
      @JsonProperty("a") BigDecimal ask,
      @JsonProperty("tr") BigDecimal trades) {

    this.last = last;
    this.timestamp = timestamp;
    this.volume = volume;
    this.high = high;
    this.low = low;
    this.bid = bid;
    this.ask = ask;
    this.trades = trades;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public long getTimestamp() {

    return this.timestamp;
  }

  public BigDecimal getVolume() {

    return this.volume;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getBid() {

    return this.bid;
  }

  public BigDecimal getAsk() {

    return this.ask;
  }

  public BigDecimal getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    return "BitcoiniumTicker [last="
        + last
        + ", timestamp="
        + timestamp
        + ", volume="
        + volume
        + ", high="
        + high
        + ", low="
        + low
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", trades="
        + trades
        + "]";
  }
}
