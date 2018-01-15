package org.knowm.xchange.bitcointoyou.dto.marketdata;

import java.math.BigDecimal;

/**
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
public class BitcointoyouLevel {

  private final BigDecimal amount;
  private final BigDecimal limit;

  public BitcointoyouLevel(BigDecimal price, BigDecimal quantity) {

    super();
    this.amount = quantity;
    this.limit = price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getLimit() {

    return limit;
  }

  @Override
  public String toString() {

    return "BitcointoyouLevel [amount=" + amount + ", limit=" + limit + "]";
  }

}
