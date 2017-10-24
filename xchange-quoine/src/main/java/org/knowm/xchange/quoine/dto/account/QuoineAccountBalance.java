package org.knowm.xchange.quoine.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class QuoineAccountBalance {

  private final String currency;
  private final BigDecimal balance;

  public QuoineAccountBalance(@JsonProperty("currency") String currency, @JsonProperty("balance") BigDecimal balance) {
    this.currency = currency;
    this.balance = balance;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "QuoineAccountBalance [currency=" + currency + ", balance=" + balance + "]";
  }

}
