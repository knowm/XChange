package org.knowm.xchange.independentreserve.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class OrderBookOrder {
  private final String orderType;
  private final BigDecimal price;
  private final BigDecimal volume;

  public OrderBookOrder(
      @JsonProperty("OrderType") String orderType,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("Volume") BigDecimal volume) {
    this.orderType = orderType;
    this.price = price;
    this.volume = volume;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "OrderBookOrder{"
        + "orderType='"
        + orderType
        + '\''
        + ", price="
        + price
        + ", volume="
        + volume
        + '}';
  }
}
