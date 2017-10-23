package org.knowm.xchange.cryptopia.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptopiaOrderBook {

  private final List<CryptopiaOrder> bids;
  private final List<CryptopiaOrder> asks;

  public CryptopiaOrderBook(@JsonProperty("Buy") List<CryptopiaOrder> bids, @JsonProperty("Sell") List<CryptopiaOrder> asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public List<CryptopiaOrder> getBids() {
    return bids;
  }

  public List<CryptopiaOrder> getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "CryptopiaOrderBook{" +
        "bids=" + bids +
        ", asks=" + asks +
        '}';
  }
}
