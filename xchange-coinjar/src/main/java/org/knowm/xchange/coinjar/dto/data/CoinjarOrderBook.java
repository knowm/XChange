package org.knowm.xchange.coinjar.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CoinjarOrderBook {

  public final List<List<String>> bids;

  public final List<List<String>> asks;

  public CoinjarOrderBook(
      @JsonProperty("bids") List<List<String>> bids,
      @JsonProperty("asks") List<List<String>> asks) {
    this.bids = bids;
    this.asks = asks;
  }
}
