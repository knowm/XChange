package org.knowm.xchange.hitbtc.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcOrderBook {

  private final HitbtcOrderLimit[] asks;
  private final HitbtcOrderLimit[] bids;

  public HitbtcOrderBook(@JsonProperty("ask") HitbtcOrderLimit[] asks, @JsonProperty("bid") HitbtcOrderLimit[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public HitbtcOrderLimit[] getAsks() {
    return asks;
  }

  public HitbtcOrderLimit[] getBids() {
    return bids;
  }

  @Override
  public String toString() {

    StringBuilder asksBuilder = new StringBuilder();
    StringBuilder bidsBuilder = new StringBuilder();

    for (HitbtcOrderLimit ask : getAsks()) {
      asksBuilder.append(ask + ";");
    }

    for (HitbtcOrderLimit bid : getBids()) {
      bidsBuilder.append(bid + ";");
    }

    return "HitbtcOrderBook{" + "asks=" + asksBuilder + ", bids=" + bidsBuilder + '}';
  }
}