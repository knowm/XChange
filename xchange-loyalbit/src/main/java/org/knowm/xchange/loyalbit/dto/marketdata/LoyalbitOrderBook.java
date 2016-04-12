package org.knowm.xchange.loyalbit.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import si.mazi.rescu.ExceptionalReturnContentException;

/**
 * @author Matija Mazi
 */
public class LoyalbitOrderBook {

  private final List<List<BigDecimal>> bids;
  private final List<List<BigDecimal>> asks;

  public LoyalbitOrderBook(@JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("status") Integer status) throws ExceptionalReturnContentException {
    if (Objects.equals(status, 0)) {
      throw new ExceptionalReturnContentException("Status indicates failure: " + status);
    }
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
    return "LoyalbitOrderBook [bids=" + bids + ", asks=" + asks + "]";
  }

}
