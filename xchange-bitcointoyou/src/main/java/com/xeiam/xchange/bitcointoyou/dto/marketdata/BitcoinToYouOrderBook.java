package com.xeiam.xchange.bitcointoyou.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouOrderBook {

  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  /**
   * Constructor
   *
   * @param bids
   * @param asks
   */
  public BitcoinToYouOrderBook(@JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks) {

    this.bids = bids;
    this.asks = asks;
  }

  /** (price, amount) */
  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  /** (price, amount) */
  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return "BitcoinToYouOrderBook [bids=" + bids + ", asks=" + asks + "]";
  }

}
