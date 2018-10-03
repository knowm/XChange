package org.knowm.xchange.quadrigacx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import si.mazi.rescu.ExceptionalReturnContentException;
import si.mazi.rescu.serialization.jackson.serializers.TimestampDeserializer;

public class QuadrigaCxTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final Date timestamp;

  public QuadrigaCxTicker(
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("timestamp") @JsonDeserialize(using = TimestampDeserializer.class)
          Date timestamp) {

    if (last == null) {
      throw new ExceptionalReturnContentException("No last in response.");
    }
    this.last = last;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
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

  public Date getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "QuadrigaCxTicker [last="
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
        + ", timestamp="
        + timestamp
        + "]";
  }
}
