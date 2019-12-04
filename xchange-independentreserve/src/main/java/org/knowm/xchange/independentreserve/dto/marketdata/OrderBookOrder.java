package org.knowm.xchange.independentreserve.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class OrderBookOrder {
  private final String guid;
  private final BigDecimal price;
  private final BigDecimal volume;

  public OrderBookOrder(
      @JsonProperty("Guid") String guid,
      @JsonProperty("Price") BigDecimal price,
      @JsonProperty("Volume") BigDecimal volume) {
    this.guid = guid;
    this.price = price;
    this.volume = volume;
  }

  public String getGuid() {
    return guid;
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
        + "guid='"
        + guid
        + '\''
        + ", price="
        + price
        + ", volume="
        + volume
        + '}';
  }
}
