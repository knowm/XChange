package org.knowm.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyPublicOrder {

  private final BigDecimal price;
  private final BigDecimal quantity;
  private final BigDecimal total;

  @JsonCreator
  public CryptsyPublicOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("total") BigDecimal total) {

    this.price = price;
    this.quantity = quantity;
    this.total = total;
  }

  /**
   * @return the sellPrice
   */
  public BigDecimal getPrice() {

    return price;
  }

  /**
   * @return the quantity
   */
  public BigDecimal getQuantity() {

    return quantity;
  }

  /**
   * @return the total
   */
  public BigDecimal getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return "CryptsyPublicOrder [price=" + price + ", quantity=" + quantity + ", total=" + total + "]";
  }

}