package com.xeiam.xchange.justcoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class JustcoinDepth {

  private List<List<BigDecimal>> bids;

  private List<List<BigDecimal>> asks;

  /**
   * Constructor
   * 
   * @param bids
   * @param asks
   */
  public JustcoinDepth(@JsonProperty("bids") final List<List<BigDecimal>> bids, @JsonProperty("asks") final List<List<BigDecimal>> asks) {

    this.bids = bids;
    this.asks = asks;
  }

  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return "JustcoinDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }
}
