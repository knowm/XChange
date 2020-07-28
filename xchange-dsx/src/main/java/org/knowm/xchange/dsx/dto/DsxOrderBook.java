package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DsxOrderBook {

  private final DsxOrderLimit[] asks;
  private final DsxOrderLimit[] bids;

  public DsxOrderBook(
      @JsonProperty("ask") DsxOrderLimit[] asks, @JsonProperty("bid") DsxOrderLimit[] bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public DsxOrderLimit[] getAsks() {
    return asks;
  }

  public DsxOrderLimit[] getBids() {
    return bids;
  }

  @Override
  public String toString() {

    StringBuilder asksBuilder = new StringBuilder();
    StringBuilder bidsBuilder = new StringBuilder();

    for (DsxOrderLimit ask : getAsks()) {
      asksBuilder.append(ask + ";");
    }

    for (DsxOrderLimit bid : getBids()) {
      bidsBuilder.append(bid + ";");
    }

    return "DsxOrderBook{" + "asks=" + asksBuilder + ", bids=" + bidsBuilder + '}';
  }
}
