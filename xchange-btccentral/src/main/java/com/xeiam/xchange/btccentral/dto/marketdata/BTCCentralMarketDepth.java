package com.xeiam.xchange.btccentral.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * @author kpysniak
 */
public class BTCCentralMarketDepth {

  private final BTCCentralMarketOrder[] bids;
  private final BTCCentralMarketOrder[] asks;

  /**
   *
   * @param bids
   * @param asks
   */
  public BTCCentralMarketDepth(@JsonProperty("bids") BTCCentralMarketOrder[] bids,
                               @JsonProperty("asks") BTCCentralMarketOrder[] asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public BTCCentralMarketOrder[] getBids() {
    return bids;
  }

  public BTCCentralMarketOrder[] getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "BTCCentralMarketDepth{" +
        "bids=" + Arrays.toString(bids) +
        ", asks=" + Arrays.toString(asks) +
        '}';
  }
}
