package org.knowm.xchange.wex.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/** Data object representing depth from Wex */
public class WexDepth {

  private final List<BigDecimal[]> asks;
  private final List<BigDecimal[]> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  public WexDepth(
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

    StringBuilder sb = new StringBuilder("WexDepth [asks=");
    for (BigDecimal[] a : asks) {
      sb.append("[").append(a[0].toString()).append(",").append(a[1].toString()).append("],");
    }
    sb.append(" bids=");
    for (BigDecimal[] b : bids) {
      sb.append("[").append(b[0].toString()).append(",").append(b[1].toString()).append("],");
    }
    sb.append("]");

    return sb.toString();
  }
}
