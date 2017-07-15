package org.knowm.xchange.dsx.dto.account;

import java.math.BigDecimal;

/**
 * @author Mikhail Wall
 */

public class DSXCurrencyAmount {
  private BigDecimal total;
  private BigDecimal available;

  public DSXCurrencyAmount(BigDecimal total, BigDecimal available) {
    this.total = total;
    this.available = available;
  }

  // constructor for correct json parsing
  public DSXCurrencyAmount() {}

  public BigDecimal getTotal() {
    return total;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  @Override
  public String toString() {
    return "DSXCurrencyAmount{" +
        "total=" + total +
        ", available=" + available +
        '}';
  }
}
