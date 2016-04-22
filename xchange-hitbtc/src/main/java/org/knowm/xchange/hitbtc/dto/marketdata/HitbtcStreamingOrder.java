package org.knowm.xchange.hitbtc.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcStreamingOrder {

  private final BigDecimal price;
  private final BigDecimal size;

  /**
   * Constructor
   *
   * @param price
   * @param size
   */
  public HitbtcStreamingOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size) {

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

    return "HitbtcStreamingOrder{price=" + price + ", size=" + size + "}";
  }
}
