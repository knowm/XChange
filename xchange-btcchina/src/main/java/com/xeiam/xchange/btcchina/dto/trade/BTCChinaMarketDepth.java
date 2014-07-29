package com.xeiam.xchange.btcchina.dto.trade;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaMarketDepth {

  private final BTCChinaMarketDepthOrder[] bids;
  private final BTCChinaMarketDepthOrder[] asks;

  public BTCChinaMarketDepth(@JsonProperty("bid") BTCChinaMarketDepthOrder[] bids, @JsonProperty("ask") BTCChinaMarketDepthOrder[] asks) {

    this.bids = bids;
    this.asks = asks;
  }

  public BTCChinaMarketDepthOrder[] getBids() {

    return bids;
  }

  public BTCChinaMarketDepthOrder[] getAsks() {

    return asks;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
