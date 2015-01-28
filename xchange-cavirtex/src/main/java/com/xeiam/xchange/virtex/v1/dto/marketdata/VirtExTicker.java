package com.xeiam.xchange.virtex.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Ticker from VirtEx
 */
public final class VirtExTicker {

  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;

  /**
   * Constructor
   * 
   * @param high
   * @param low
   * @param volume
   * @param last
   */

  @Deprecated
  public VirtExTicker(@JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("last") BigDecimal last) {

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

  @Override
  public String toString() {

    return "VirtExTicker [last=" + last + ", high=" + high + ", low=" + low + ", volume=" + volume + "]";

  }

}
