package org.knowm.xchange.bitcoinde.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeOrder {

  private final BigDecimal price;
  private final BigDecimal amount;

  public BitcoindeOrder(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {

    this.price = price;
    this.amount = amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "BitcoindeOrder{" +
        "price=" + price +
        ", amount=" + amount +
        '}';
  }
}
