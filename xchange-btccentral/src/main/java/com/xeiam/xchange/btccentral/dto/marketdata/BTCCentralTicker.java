package com.xeiam.xchange.btccentral.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author kpysniak
 */
public final class BTCCentralTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal midpoint;
  private final long at;
  private final BigDecimal price;
  private final BigDecimal vwap;
  private final BigDecimal variation;
  private final String currency;


  /**
   * Constructor
   *
   * @param high
   * @param low
   * @param volume
   * @param bid
   * @param ask
   * @param midpoint
   * @param at
   * @param price
   * @param vwap
   * @param variation
   * @param currency
   */
  public BTCCentralTicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
                          @JsonProperty("volume") BigDecimal volume, @JsonProperty("bid") BigDecimal bid,
                          @JsonProperty("ask") BigDecimal ask, @JsonProperty("midpoint") BigDecimal midpoint,
                          @JsonProperty("at") long at, @JsonProperty("price") BigDecimal price,
                          @JsonProperty("vwap") BigDecimal vwap, @JsonProperty("variation") BigDecimal variation,
                          @JsonProperty("currency") String currency) {

    this.high = high;
    this.low = low;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;
    this.midpoint = midpoint;
    this.at = at;
    this.price = price;
    this.vwap = vwap;
    this.variation = variation;
    this.currency = currency;

  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
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

  public BigDecimal getMidpoint() {
    return midpoint;
  }

  public long getAt() {
    return at;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVwap() {
    return vwap;
  }

  public BigDecimal getVariation() {
    return variation;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return "BTCCentralTicker{" +
        "high=" + high +
        ", low=" + low +
        ", volume=" + volume +
        ", bid=" + bid +
        ", ask=" + ask +
        ", midpoint=" + midpoint +
        ", at=" + at +
        ", price=" + price +
        ", vwap=" + vwap +
        ", variation=" + variation +
        ", currency='" + currency + '\'' +
        '}';
  }
}
