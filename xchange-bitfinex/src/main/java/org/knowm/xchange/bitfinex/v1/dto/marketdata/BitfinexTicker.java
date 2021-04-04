package org.knowm.xchange.bitfinex.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexTicker {

  private final BigDecimal mid;
  private final BigDecimal bid;
  private final BigDecimal bidSize;
  private final BigDecimal ask;
  private final BigDecimal askSize;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final double timestamp;

  /**
   * @param mid
   * @param bid
   * @param bidSize
   * @param ask
   * @param askSize
   * @param low
   * @param high
   * @param last
   * @param timestamp
   * @param volume
   */
  public BitfinexTicker(
      @JsonProperty("mid") BigDecimal mid,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("askSize") BigDecimal askSize,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("last_price") BigDecimal last,
      @JsonProperty("timestamp") double timestamp,
      @JsonProperty("volume") BigDecimal volume) {

    this.mid = mid;
    this.bid = bid;
    this.bidSize = bidSize;
    this.ask = ask;
    this.askSize = askSize;
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

  public BigDecimal getBidSize() {

    return bidSize;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getAskSize() {

    return askSize;
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
        + ", bidSize="
        + bidSize
        + ", ask="
        + ask
        + ", askSize="
        + askSize
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
