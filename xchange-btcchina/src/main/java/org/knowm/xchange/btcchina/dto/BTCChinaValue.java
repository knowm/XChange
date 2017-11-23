package org.knowm.xchange.btcchina.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaValue {

  private final String currency;
  private final String symbol;
  private final BigDecimal amount;
  private final String amountInteger;
  private final BigDecimal amountDecimal;

  /**
   * Constructor
   *
   * @param currency
   * @param symbol
   * @param amount
   * @param amountInteger
   * @param amountDecimal
   */
  public BTCChinaValue(@JsonProperty("currency") String currency, @JsonProperty("symbol") String symbol, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amount_integer") String amountInteger, @JsonProperty("amount_decimal") BigDecimal amountDecimal) {

    this.currency = currency;
    this.symbol = symbol;
    this.amount = amount;
    this.amountInteger = amountInteger;
    this.amountDecimal = amountDecimal;
  }

  public String getCurrency() {

    return currency;
  }

  public String getSymbol() {

    return symbol;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getAmountInteger() {

    return amountInteger;
  }

  public BigDecimal getAmountDecimal() {

    return amountDecimal;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaValue{currency=%s, symbol=%s, amount=%s, amountInteger=%s, amountDecimal=%s}", currency, symbol, amount,
        amountInteger, amountDecimal);
  }

}
