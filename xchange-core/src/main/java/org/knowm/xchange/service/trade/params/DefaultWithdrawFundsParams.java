package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.knowm.xchange.currency.Currency;

public class DefaultWithdrawFundsParams implements WithdrawFundsParams {
  public final String address;

  public final Currency currency;

  public final BigDecimal amount;

  @Nullable public final BigDecimal commission;

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public DefaultWithdrawFundsParams(
      String address, Currency currency, BigDecimal amount, BigDecimal commission) {
    this.address = address;
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  public String getAddress() {
    return address;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Nullable
  public BigDecimal getCommission() {
    return commission;
  }

  @Override
  public String toString() {
    return "DefaultWithdrawFundsParams{"
        + "address='"
        + getAddress()
        + '\''
        + ", currency="
        + getCurrency()
        + ", amount="
        + getAmount()
        + ", commission="
        + getCommission()
        + '}';
  }
}
