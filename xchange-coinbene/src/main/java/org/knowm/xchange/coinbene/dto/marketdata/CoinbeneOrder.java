package org.knowm.xchange.coinbene.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbeneOrder {
  private final BigDecimal price;
  private final BigDecimal quantity;

  public CoinbeneOrder(
      @JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity) {
    this.price = price;
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  @Override
  public String toString() {
    return "CoinbeneOrder{" + "price=" + price + ", quantity=" + quantity + '}';
  }
}
