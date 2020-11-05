package org.knowm.xchange.bitbns.dto;

import java.math.BigDecimal;

public class Asks {

  private BigDecimal amount;
  private long amount_int;
  private BigDecimal price;
  private long price_int;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public long getAmount_int() {
    return amount_int;
  }

  public void setAmount_int(long amount_int) {
    this.amount_int = amount_int;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public long getPrice_int() {
    return price_int;
  }

  public void setPrice_int(long price_int) {
    this.price_int = price_int;
  }

  @Override
  public String toString() {
    return "Asks [amount="
        + amount
        + ", amount_int="
        + amount_int
        + ", price="
        + price
        + ", price_int="
        + price_int
        + "]";
  }
}
