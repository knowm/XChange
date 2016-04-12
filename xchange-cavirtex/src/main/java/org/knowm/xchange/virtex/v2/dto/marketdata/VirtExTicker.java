package org.knowm.xchange.virtex.v2.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from VirtEx
 */
public final class VirtExTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal sell;
  private final BigDecimal buy;
  private final BigDecimal volume;

  /**
   * @param sell
   * @param buy
   * @param high
   * @param low
   * @param volume
   * @param last
   */
  public VirtExTicker(@JsonProperty("sell") BigDecimal sell, @JsonProperty("buy") BigDecimal buy, @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume, @JsonProperty("last") BigDecimal last) {

    this.sell = sell;
    this.buy = buy;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.last = last;
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

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getSell() {

    return sell;
  }

  @Override
  public String toString() {

    return "VirtExTicker [last=" + last + ", high=" + high + ", low=" + low + ", buy=" + buy + ", sell=" + sell + ", volume=" + volume + "]";

  }

}
