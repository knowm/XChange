package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

public class Orders {

  private final BigDecimal[] price;
  private final int[] level;
  private final BigDecimal[] amount;
  private final BigDecimal[] accuAmount;

  public Orders(BigDecimal[] price, int[] level, BigDecimal[] amount, BigDecimal[] accuAmount) {
    super();
    this.price = price;
    this.level = level;
    this.amount = amount;
    this.accuAmount = accuAmount;
  }

  public BigDecimal[] getPrice() {
    return price;
  }

  public int[] getLevel() {
    return level;
  }

  public BigDecimal[] getAmount() {
    return amount;
  }

  public BigDecimal[] getAccuAmount() {
    return accuAmount;
  }

}
