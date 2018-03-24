package org.knowm.xchange.therock.dto.marketdata;

import java.math.BigDecimal;

public class TheRockBid {
  private BigDecimal price, amount;

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return String.format("TheRockBid{price=%s, amount=%s}", price, amount);
  }
}
