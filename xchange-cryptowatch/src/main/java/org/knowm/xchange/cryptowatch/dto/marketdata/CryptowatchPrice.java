package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CryptowatchPrice {

  private final BigDecimal price;

  public CryptowatchPrice(@JsonProperty("price") BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "CryptowatchPrice{" + "price=" + price + '}';
  }
}
