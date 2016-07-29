package org.knowm.xchange.btcchina.dto.trade;

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

  @Override
  public String toString() {

    return "BTCChinaMarketDepthObject [marketDepth=" + marketDepth + "]";
  }

}
