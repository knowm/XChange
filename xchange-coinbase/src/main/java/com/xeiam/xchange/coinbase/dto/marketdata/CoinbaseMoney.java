package com.xeiam.xchange.coinbase.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;

/**
 * @author jamespedwards42
 */
@JsonDeserialize(using = CoinbaseMoneyDeserializer.class)
public class CoinbaseMoney {

  private final String currency;
  private final BigDecimal amount;

  public CoinbaseMoney(final String currency, final BigDecimal amount) {

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

    return "CoinbaseMoney [currency=" + currency + ", amount=" + amount + "]";
  }

}