package org.knowm.xchange.bitcurex.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcurexDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public BitcurexDepth(@JsonProperty("asks") List<BigDecimal[]> asks, @JsonProperty("bids") List<BigDecimal[]> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<BigDecimal[]> getAsks() {

    return this.asks;
  }

  public List<BigDecimal[]> getBids() {

    return this.bids;
  }

  @Override
  public String toString() {

    return "BitcurexDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
