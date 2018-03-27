package org.knowm.xchange.vircurex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/** Data object representing depth from Vircurex */
public class VircurexDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public VircurexDepth(
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

    return "VircurexDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }
}
