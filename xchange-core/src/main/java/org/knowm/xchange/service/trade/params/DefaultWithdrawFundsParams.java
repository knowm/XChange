package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class DefaultWithdrawFundsParams implements WithdrawFundsParams {
  public final String address;
  public final Currency currency;
  public final BigDecimal amount;

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this.address = address;
    this.currency = currency;
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "DefaultWithdrawFundsParams{" +
        "address='" + address + '\'' +
        ", currency=" + currency +
        ", amount=" + amount +
        '}';
  }
}
