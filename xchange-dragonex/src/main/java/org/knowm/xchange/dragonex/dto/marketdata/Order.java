package org.knowm.xchange.dragonex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Order {

  public final BigDecimal price;
  public final BigDecimal volume;

  public Order(@JsonProperty("price") BigDecimal price, @JsonProperty("volume") BigDecimal volume) {
    this.price = price;
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "Order ["
        + (price != null ? "price=" + price + ", " : "")
        + (volume != null ? "volume=" + volume : "")
        + "]";
  }
}
