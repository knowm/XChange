package com.xeiam.xchange.btcchina.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from BTCChina
 */
public final class BTCChinaDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public BTCChinaDepth(@JsonProperty("asks") List<BigDecimal[]> asks, @JsonProperty("bids") List<BigDecimal[]> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<BigDecimal[]> getAsks() {

    return this.asks;
  }

  public List<BigDecimal[]> getBids() {

    return this.bids;
  }

  @Override
  public String toString() {

    return "BTCChinaDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }
}
