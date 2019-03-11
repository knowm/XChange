package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.cobinhood.dto.CobinhoodOrderSide;

public class CobinhoodTrade {
  private final String id;
  private final BigDecimal price;
  private final BigDecimal size;
  private final CobinhoodOrderSide makerSide;
  private final Long timestamp;

  public CobinhoodTrade(
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("maker_side") CobinhoodOrderSide makerSide,
      @JsonProperty("timestamp") Long timestamp) {
    this.id = id;
    this.price = price;
    this.size = size;
    this.makerSide = makerSide;
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public CobinhoodOrderSide getMakerSide() {
    return makerSide;
  }

  public Long getTimestamp() {
    return timestamp;
  }
}
