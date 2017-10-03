package org.knowm.xchange.currency;

import java.math.BigDecimal;

public class CurrencyAmount {

  private final String currency;
  private final BigDecimal amount;

  public CurrencyAmount(String currency, final BigDecimal amount) {

    this.currency = currency;
    this.amount = amount;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "CurrencyAmount [" + amount + " " + currency + "]";
  }
}
