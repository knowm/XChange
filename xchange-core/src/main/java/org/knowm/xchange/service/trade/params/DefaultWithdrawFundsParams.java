package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.Currency;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class DefaultWithdrawFundsParams implements WithdrawFundsParams {
  public final String address;

  public final Currency currency;

  public final BigDecimal amount;

  @Nullable
  public final BigDecimal commission;

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount, BigDecimal commission) {
    this.address = address;
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  @Override
  public String toString() {
    return "DefaultWithdrawFundsParams{" +
        "address='" + address + '\'' +
        ", currency=" + currency +
        ", amount=" + amount +
        ", commission=" + commission +
        '}';
  }
}
