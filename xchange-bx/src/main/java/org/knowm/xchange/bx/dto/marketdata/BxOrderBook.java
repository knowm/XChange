package org.knowm.xchange.bx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BxOrderBook {

  private final BxOrderBookItem bids;
  private final BxOrderBookItem asks;

  public BxOrderBook(
      @JsonProperty("bids") BxOrderBookItem bids, @JsonProperty("asks") BxOrderBookItem asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public BxOrderBookItem getBids() {
    return bids;
  }

  public BxOrderBookItem getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "BxOrderBook{" + "bids=" + bids + ", asks=" + asks + '}';
  }
}
