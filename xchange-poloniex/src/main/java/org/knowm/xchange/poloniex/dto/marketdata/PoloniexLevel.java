package org.knowm.xchange.poloniex.dto.marketdata;

import java.math.BigDecimal;

/** @author Zach Holmes */
public class PoloniexLevel {

  private BigDecimal amount;
  private BigDecimal limit;

  public PoloniexLevel(BigDecimal amount, BigDecimal limit) {

    super();
    this.amount = amount;
    this.limit = limit;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getLimit() {

    return limit;
  }

  @Override
  public String toString() {

    return "PoloniexLevel [amount=" + amount + ", limit=" + limit + "]";
  }
}
