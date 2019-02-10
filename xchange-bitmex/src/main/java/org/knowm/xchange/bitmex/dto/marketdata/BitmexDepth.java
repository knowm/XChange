package org.knowm.xchange.bitmex.dto.marketdata;

import java.util.List;

/** Data object representing depth from bitmex */
public class BitmexDepth {

  private final List<BitmexPublicOrder> asks;
  private final List<BitmexPublicOrder> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public BitmexDepth(List<BitmexPublicOrder> asks, List<BitmexPublicOrder> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<BitmexPublicOrder> getAsks() {

    return asks;
  }

  public List<BitmexPublicOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "bitmexDepth [asks=" + asks + ", bids=" + bids + "]";
  }
}
