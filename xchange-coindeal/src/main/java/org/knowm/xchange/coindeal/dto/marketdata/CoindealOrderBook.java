package org.knowm.xchange.coindeal.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CoindealOrderBook {

  @JsonProperty("asks")
  private final List<CoindealOrderBookEntry> asks;

  @JsonProperty("bids")
  private final List<CoindealOrderBookEntry> bids;

  public CoindealOrderBook(
      @JsonProperty("ask") List<CoindealOrderBookEntry> askList,
      @JsonProperty("bid") List<CoindealOrderBookEntry> bidList) {
    this.asks = askList;
    this.bids = bidList;
  }

  public List<CoindealOrderBookEntry> getAsks() {
    return asks;
  }

  public List<CoindealOrderBookEntry> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "CoindealOrderBook{" + "asks=" + asks + ", bids=" + bids + '}';
  }
}
