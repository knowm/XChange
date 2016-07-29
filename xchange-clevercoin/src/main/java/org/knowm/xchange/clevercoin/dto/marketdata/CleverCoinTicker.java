package org.knowm.xchange.clevercoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karsten Nilsen
 */
public final class CleverCoinTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal previous;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final long timestamp;

  /**
   * Constructor
   * 
   * @param last
   * @param high
   * @param low
   * @param previous
   * @param volume
   * @param bid
   * @param ask
   */
  public CleverCoinTicker(@JsonProperty("last") BigDecimal last, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
      @JsonProperty("previous") BigDecimal previous, @JsonProperty("volume") BigDecimal volume, @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask, @JsonProperty("timestamp") long timestamp) {

    this.last = last;
    this.high = high;
    this.low = low;
    this.previous = previous;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;

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

  public BigDecimal getPrevious() {

    return previous;
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

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "CleverCoinTicker [last=" + last + ", high=" + high + ", low=" + low + ", previous=" + previous + ", volume=" + volume + ", bid=" + bid
        + ", ask=" + ask + ", timestamp=" + timestamp + "]";
  }

}
