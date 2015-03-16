package com.xeiam.xchange.yacuna.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yingzhe on 12/26/2014.
 */
public class YacunaCurrencyAmountPair {

  private final String currency;
  private final BigDecimal amount;

  public YacunaCurrencyAmountPair(@JsonProperty("currency") String currency, @JsonProperty("amount") BigDecimal amount) {

    this.currency = currency;
    this.amount = amount;
  }

  public String getCurrency() {

    return this.currency;
  }

  public BigDecimal getAmount() {

    return this.amount;
  }

  @Override
  public String toString() {

    return String.format("[currency: %s, amount: %s]", currency, amount);
  }
}
