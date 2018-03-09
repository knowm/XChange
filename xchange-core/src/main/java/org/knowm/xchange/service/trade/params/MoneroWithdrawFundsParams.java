package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.knowm.xchange.currency.Currency;

public class MoneroWithdrawFundsParams extends DefaultWithdrawFundsParams {
  @Nullable
  public final String paymentId;//optional

  public MoneroWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public MoneroWithdrawFundsParams(String address, Currency currency, BigDecimal amount, String paymentId) {
    super(address, currency, amount);
    this.paymentId = paymentId;
  }

  @Override
  public String toString() {
    return "MoneroWithdrawFundsParams{" +
        "address='" + address + '\'' +
        ", paymentId='" + paymentId + '\'' +
        ", currency=" + currency +
        ", amount=" + amount +
        ", commission=" + commission +
        '}';
  }
}
