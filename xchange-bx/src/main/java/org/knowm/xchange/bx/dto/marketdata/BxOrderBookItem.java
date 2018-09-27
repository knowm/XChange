package org.knowm.xchange.bx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxOrderBookItem {

  private final BigDecimal total;
  private final BigDecimal volume;
  private final BigDecimal highBid;

  public BxOrderBookItem(
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("highbid") BigDecimal highBid) {
    this.total = total;
    this.volume = volume;
    this.highBid = highBid;
  }

  public BigDecimal getHighBid() {
    return highBid;
  }

  @Override
  public String toString() {
    return "BxOrderBookItem{"
        + "total="
        + total
        + ", volume="
        + volume
        + ", highbid="
        + highBid
        + '}';
  }
}
