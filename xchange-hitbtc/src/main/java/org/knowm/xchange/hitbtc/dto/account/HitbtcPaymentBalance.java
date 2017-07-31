package org.knowm.xchange.hitbtc.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HitbtcPaymentBalance {

  private final String currencyCode;
  private final BigDecimal balance;

  public HitbtcPaymentBalance(@JsonProperty("currency_code") String currencyCode, @JsonProperty("balance") BigDecimal balance) {
    this.currencyCode = currencyCode;
    this.balance = balance;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "HitbtcPaymentBalance{" +
            "currencyCode='" + currencyCode + '\'' +
            ", balance=" + balance +
            '}';
  }
}
