package org.knowm.xchange.clevercoin.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karsten Nilsen
 */
public final class CleverCoinBalance {

  private final String currency;
  private final BigDecimal balance;

  private final String error;

  /**
   * Constructor
   * 
   * @param currency
   * @param btcBalance
   * @param eurBalance
   */
  public CleverCoinBalance(@JsonProperty("currency") String currency, @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("error") String error) {

    this.currency = currency;
    this.balance = balance;
    this.error = error;
  }

  public BigDecimal getBalance() {

    return balance;
  }

  public String getCurrency() {

    return currency;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("Balance{currency=%s, balance=%s}", currency, balance);
  }
}
