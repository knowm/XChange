package org.knowm.xchange.itbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class ItBitDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public ItBitDepth(
      @JsonProperty("asks") List<BigDecimal[]> asks,
      @JsonProperty("bids") List<BigDecimal[]> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<BigDecimal[]> getAsks() {

    return asks;
  }

  public List<BigDecimal[]> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "ItBitDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }
}
