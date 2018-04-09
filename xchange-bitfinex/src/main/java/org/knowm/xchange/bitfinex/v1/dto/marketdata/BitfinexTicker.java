package org.knowm.xchange.bitfinex.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexTicker {

  private final BigDecimal mid;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final double timestamp;

  /**
   * @param mid
   * @param bid
   * @param ask
   * @param low
   * @param high
   * @param last
   * @param timestamp
   * @param volume
   */
  public BitfinexTicker(
      @JsonProperty("mid") BigDecimal mid,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("last_price") BigDecimal last,
      @JsonProperty("timestamp") double timestamp,
      @JsonProperty("volume") BigDecimal volume) {

    this.mid = mid;
    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = volume;
    this.high = high;
    this.low = low;
    this.timestamp = timestamp;
  }

  public BigDecimal getMid() {

    return mid;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLast_price() {

    return last;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public double getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "BitfinexTicker [mid="
        + mid
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", low="
        + low
        + ", high="
        + high
        + ", last="
        + last
        + ", timestamp="
        + timestamp
        + "]";
  }
}
