package org.knowm.xchange.jubi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 3/16/2015.
 */
public class JubiTicker {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal buy;
  private final BigDecimal sell;
  private final BigDecimal last;
  private final BigDecimal vol;

  /**
   * Constructor for JubiTicker
   *
   * @param high High price
   * @param low Low price
   * @param buy Buy price
   * @param sell Sell price
   * @param last Last price
   * @param vol Volume
   */
  public JubiTicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("sell") BigDecimal sell, @JsonProperty("last") BigDecimal last, @JsonProperty("vol") BigDecimal vol) {

    this.high = high;
    this.low = low;
    this.buy = buy;
    this.sell = sell;
    this.last = last;
    this.vol = vol;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getSell() {

    return sell;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getVol() {

    return vol;
  }

  @Override
  public String toString() {

    return "JubiTicker{" + "high=" + high + ", low=" + low + ", buy=" + buy + ", sell=" + sell + ", last=" + last + ", vol=" + vol + '}';
  }
}
