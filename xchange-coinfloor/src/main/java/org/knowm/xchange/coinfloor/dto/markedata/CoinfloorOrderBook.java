package org.knowm.xchange.coinfloor.dto.markedata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinfloorOrderBook {
  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  public CoinfloorOrderBook(@JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks) {
    this.bids = bids;
    this.asks = asks;
  }

  /**
   * (price, amount)
   */
  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  /**
   * (price, amount)
   */
  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "CoinfloorOrderBook [bids=" + bids + ", asks=" + asks + "]";
  }

}
