package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class DsxOrderLimit {

  private final BigDecimal price;
  private final BigDecimal size;

  public DsxOrderLimit(
      @JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size) {
    this.price = price;
    this.size = size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  @Override
  public String toString() {
    return "DsxOrderLimit{" + "price=" + price + ", size=" + size + '}';
  }
}
