package org.knowm.xchange.mercadobitcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinOrderBook {

  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  /**
   * Constructor
   *
   * @param bids
   * @param asks
   */
  public MercadoBitcoinOrderBook(
      @JsonProperty("bids") List<List<BigDecimal>> bids,
      @JsonProperty("asks") List<List<BigDecimal>> asks) {

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

    return "MercadoBitcoinOrderBook [bids=" + bids + ", asks=" + asks + "]";
  }
}
