package com.xeiam.xchange.btcchina.dto.trade.streaming;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BTCChinaBalance {

  private final BigDecimal amountInteger;
  private final BigDecimal amount;
  private final String symbol;
  private final int amountDecimal;
  private final String currency;

  public BTCChinaBalance(BigDecimal amountInteger, BigDecimal amount, String symbol, int amountDecimal, String currency) {

    this.amountInteger = amountInteger;
    this.amount = amount;
    this.symbol = symbol;
    this.amountDecimal = amountDecimal;
    this.currency = currency;
  }

  public BigDecimal getAmountInteger() {

    return amountInteger;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getSymbol() {

    return symbol;
  }

  public int getAmountDecimal() {

    return amountDecimal;
  }

  public String getCurrency() {

    return currency;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
