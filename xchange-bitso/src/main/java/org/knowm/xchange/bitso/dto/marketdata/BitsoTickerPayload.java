package org.knowm.xchange.bitso.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import si.mazi.rescu.ExceptionalReturnContentException;
import si.mazi.rescu.serialization.jackson.serializers.TimestampDeserializer;

/** @author Ravi Pandit */
public class BitsoTickerPayload {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final String vwap;
  private final String volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final Date timestamp;
  private final BigDecimal change24;

  public BitsoTickerPayload(
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("vwap") String vwap,
      @JsonProperty("volume") String volume,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("timestamp") @JsonDeserialize(using = TimestampDeserializer.class) Date timestamp,
      @JsonProperty("change_24")BigDecimal change24) {

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
    this.change24 = change24.setScale(4, RoundingMode.HALF_UP);
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

  public String getVwap() {

    return vwap;
  }

  public String getVolume() {

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

  public BigDecimal getChange24() {
    return change24;
  }

  @Override
  public String toString() {

    return "BitsoTicker [last="
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
