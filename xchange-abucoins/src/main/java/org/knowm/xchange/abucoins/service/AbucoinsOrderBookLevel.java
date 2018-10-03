package org.knowm.xchange.abucoins.service;

public enum AbucoinsOrderBookLevel {
  fullyAggregated,
  bestAskAndBid,
  top50AsksAndBids;

  public String toLevelParameter() {
    switch (this) {
      default:
      case fullyAggregated:
        return "0";

      case bestAskAndBid:
        return "1";

      case top50AsksAndBids:
        return "2";
    }
  }
}
