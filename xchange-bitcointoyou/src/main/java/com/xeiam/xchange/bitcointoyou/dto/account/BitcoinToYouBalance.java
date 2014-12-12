package com.xeiam.xchange.bitcointoyou.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouBalance {

  private final BigDecimal balanceAvailable;
  private final String currency;

  public BitcoinToYouBalance(@JsonProperty("balanceAvailable") BigDecimal balanceAvailable, @JsonProperty("currency") String currency) {

    this.balanceAvailable = balanceAvailable;
    this.currency = currency;
  }

  @Override
  public String toString() {

    return "BitcoinToYouBalance [" + "balanceAvailable=" + balanceAvailable + ", currency='" + currency + '\'' + ']';
  }

  public BigDecimal getBalanceAvailable() {

    return balanceAvailable;
  }

  public String getCurrency() {

    return currency;
  }

}
