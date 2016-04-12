package org.knowm.xchange.virtex.v2.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing depth from VirtEx
 */
public final class VirtExDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;
  private final String currencypair;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public VirtExDepth(@JsonProperty("currencypair") String currencypair, @JsonProperty("asks") List<BigDecimal[]> asks,
      @JsonProperty("bids") List<BigDecimal[]> bids) {

    this.asks = asks;
    this.bids = bids;
    this.currencypair = currencypair;
  }

  public List<BigDecimal[]> getAsks() {

    return asks;
  }

  public List<BigDecimal[]> getBids() {

    return bids;
  }

  public String getCurrencyPair() {

    return currencypair;
  }

  @Override
  public String toString() {

    return "VirtExDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
