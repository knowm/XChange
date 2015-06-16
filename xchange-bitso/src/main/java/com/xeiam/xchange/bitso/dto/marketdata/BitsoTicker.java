package com.xeiam.xchange.bitso.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import si.mazi.rescu.serialization.jackson.serializers.TimestampDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final Date timestamp;

  public BitsoTicker(@JsonProperty("last") BigDecimal last, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
                     @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("volume") BigDecimal volume, @JsonProperty("bid") BigDecimal bid,
                     @JsonProperty("ask") BigDecimal ask,
                     @JsonProperty("timestamp") @JsonDeserialize(using = TimestampDeserializer.class) Date timestamp) {

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

    return "BitsoTicker [last=" + last + ", high=" + high + ", low=" + low + ", vwap=" + vwap + ", volume=" + volume + ", bid=" + bid
        + ", ask=" + ask + ", timestamp=" + timestamp + "]";
  }

}
