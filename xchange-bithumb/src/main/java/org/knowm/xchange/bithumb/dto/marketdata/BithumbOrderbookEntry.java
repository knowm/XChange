package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BithumbOrderbookEntry {

  private final BigDecimal quantity;
  private final BigDecimal price;

  public BithumbOrderbookEntry(
      @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("price") BigDecimal price) {
    this.quantity = quantity;
    this.price = price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "BithumbOrderbookEntry{" + "quantity=" + quantity + ", price=" + price + '}';
  }
}
