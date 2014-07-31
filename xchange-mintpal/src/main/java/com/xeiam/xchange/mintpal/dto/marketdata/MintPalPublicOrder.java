package com.xeiam.xchange.mintpal.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class MintPalPublicOrder {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal total;

  public MintPalPublicOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("total") BigDecimal total) {

    this.price = price;
    this.amount = amount;
    this.total = total;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTotal() {

    return total;
  }

  @Override
  public String toString() {

    return "MintPalPublicOrder [price=" + price + ", amount=" + amount + ", total=" + total + "]";
  }
}
