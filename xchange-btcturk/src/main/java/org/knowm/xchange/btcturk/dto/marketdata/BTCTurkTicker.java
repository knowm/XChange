package org.knowm.xchange.btcturk.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.btcturk.dto.BTCTurkPair;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author semihunaldi
 * @author mertguner
 */
public final class BTCTurkTicker {

  private BTCTurkPair pair;
  private final BigDecimal high;
  private final BigDecimal last;
  private final long timestamp;
  private final BigDecimal bid;
  private final BigDecimal volume;
  private final BigDecimal low;
  private final BigDecimal ask;
  private final BigDecimal open;
  private final BigDecimal average;
  private final BigDecimal daily;
  private final BigDecimal dailyPercent;
  private final Currency denominatorsymbol;
  private final Currency numeratorsymbol;

  public BTCTurkTicker(
      @JsonProperty("pair") BTCTurkPair pair,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("average") BigDecimal average,
      @JsonProperty("daily") BigDecimal daily,
      @JsonProperty("dailyPercent") BigDecimal dailyPercent,
      @JsonProperty("denominatorsymbol") Currency denominatorsymbol,
      @JsonProperty("numeratorsymbol") Currency numeratorsymbol) {
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
    this.daily = daily;
    this.dailyPercent = dailyPercent;
    this.denominatorsymbol = denominatorsymbol;
    this.numeratorsymbol = numeratorsymbol;
  }

  public CurrencyPair getPair() {
    return pair.pair;
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

  public BigDecimal getDaily() {
    return daily;
  }

  public BigDecimal getDailyPercent() {
    return dailyPercent;
  }

  public Currency getDenominatorsymbol() {
    return denominatorsymbol;
  }

  public Currency getNumeratorsymbol() {
    return numeratorsymbol;
  }

  @Override
  public String toString() {
    return "BTCTurkTicker [high="
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
        + ", daily="
        + daily
        + ", dailyPercent="
        + dailyPercent
        + ", denominatorsymbol="
        + denominatorsymbol
        + ", numeratorsymbol="
        + numeratorsymbol
        + "]";
  }
}
