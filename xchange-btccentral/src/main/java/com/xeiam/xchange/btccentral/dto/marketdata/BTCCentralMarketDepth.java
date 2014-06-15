package com.xeiam.xchange.btccentral.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDepth {

  private final List<BTCCentralMarketOrder> bids;
  private final List<BTCCentralMarketOrder> asks;

  /**
   * @param bids
   * @param asks
   */
  public BTCCentralMarketDepth(@JsonProperty("bids") List<BTCCentralMarketOrder> bids, @JsonProperty("asks") List<BTCCentralMarketOrder> asks) {

    this.bids = bids;
    this.asks = asks;
  }

  public List<BTCCentralMarketOrder> getBids() {

    return bids;
  }

  public List<BTCCentralMarketOrder> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return "BTCCentralMarketDepth{" + "bids=" + bids + ", asks=" + asks + '}';
  }
}
