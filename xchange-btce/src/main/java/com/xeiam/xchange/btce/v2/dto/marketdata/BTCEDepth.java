package com.xeiam.xchange.btce.v2.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from BTCE
 */
@Deprecated
public class BTCEDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public BTCEDepth(@JsonProperty("asks") List<BigDecimal[]> asks, @JsonProperty("bids") List<BigDecimal[]> bids) {

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

    return "BTCEDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
