package com.xeiam.xchange.btcchina.dto.trade;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaMarketDepthObject {

  private BTCChinaMarketDepth marketDepth;

  public BTCChinaMarketDepthObject(@JsonProperty("market_depth") BTCChinaMarketDepth marketDepth) {

    this.marketDepth = marketDepth;
  }

  public BTCChinaMarketDepth getMarketDepth() {

    return marketDepth;
  }

  public void setMarketDepth(BTCChinaMarketDepth marketDepth) {

    this.marketDepth = marketDepth;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
