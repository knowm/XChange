package org.knowm.xchange.btc38.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 12/18/2014.
 */
public class Btc38Ticker {

  private final BigDecimal buy;
  private final BigDecimal high;
  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal sell;
  private final BigDecimal vol;

  /**
   * Constructor for Btc38Ticker without data
   *
   * @param emptyString Not used
   */
  public Btc38Ticker(String emptyString) {

    this.buy = null;
    this.high = null;
    this.last = null;
    this.low = null;
    this.sell = null;
    this.vol = null;
  }

  /**
   * Constructor for Btc38Ticker
   *
   * @param buy Buy price
   * @param high High price
   * @param last Last price
   * @param low Low price
   * @param sell Sell price
   * @param vol Volume
   */
  public Btc38Ticker(@JsonProperty("buy") BigDecimal buy, @JsonProperty("high") BigDecimal high, @JsonProperty("last") BigDecimal last,
      @JsonProperty("low") BigDecimal low, @JsonProperty("sell") BigDecimal sell, @JsonProperty("vol") BigDecimal vol) {

    this.buy = buy;
    this.high = high;
    this.last = last;
    this.low = low;
    this.sell = sell;
    this.vol = vol;
  }

  public BigDecimal getBuy() {

    return this.buy;
  }

  public BigDecimal getHigh() {

    return this.high;
  }

  public BigDecimal getLast() {

    return this.last;
  }

  public BigDecimal getLow() {

    return this.low;
  }

  public BigDecimal getSell() {

    return this.sell;
  }

  public BigDecimal getVol() {

    return this.vol;
  }

  @Override
  public String toString() {

    return "Btc38Ticker{" + "buy=" + buy + ", high=" + high + ", last=" + last + ", low=" + low + ", sell=" + sell + ", vol=" + vol + '}';
  }
}
