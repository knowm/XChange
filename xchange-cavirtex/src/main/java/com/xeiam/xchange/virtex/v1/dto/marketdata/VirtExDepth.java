package com.xeiam.xchange.virtex.v1.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from VirtEx
 */

@Deprecated
public final class VirtExDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public VirtExDepth(@JsonProperty("asks") List<BigDecimal[]> asks, @JsonProperty("bids") List<BigDecimal[]> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<BigDecimal[]> getAsks() {

    return asks;
  }

  public List<BigDecimal[]> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "VirtExDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
