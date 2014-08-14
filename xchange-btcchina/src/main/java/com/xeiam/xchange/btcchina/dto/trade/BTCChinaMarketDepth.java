package com.xeiam.xchange.btcchina.dto.trade;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaMarketDepth {

  private final BTCChinaMarketDepthOrder[] bids;
  private final BTCChinaMarketDepthOrder[] asks;
  private final long date;

  public BTCChinaMarketDepth(@JsonProperty("bid") BTCChinaMarketDepthOrder[] bids, @JsonProperty("ask") BTCChinaMarketDepthOrder[] asks, @JsonProperty("date") long date) {

    this.bids = bids;
    this.asks = asks;
    this.date = date;
  }

  public BTCChinaMarketDepthOrder[] getBids() {

    return bids;
  }

  public BTCChinaMarketDepthOrder[] getAsks() {

    return asks;
  }

  public long getDate() {

    return date;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
