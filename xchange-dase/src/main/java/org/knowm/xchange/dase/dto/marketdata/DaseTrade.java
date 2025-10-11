package org.knowm.xchange.dase.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DaseTrade {

  private final long time;
  private final String id;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String makerSide;

  @JsonCreator
  public DaseTrade(
      @JsonProperty("time") Number time,
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("maker_side") String makerSide) {
    this.time = time == null ? 0L : time.longValue();
    this.id = id;
    this.price = price;
    this.size = size;
    this.makerSide = makerSide;
  }

  public long getTime() {
    return time;
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

  public String getMakerSide() {
    return makerSide;
  }
}
