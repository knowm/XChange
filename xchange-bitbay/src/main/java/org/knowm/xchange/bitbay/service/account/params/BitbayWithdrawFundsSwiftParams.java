package org.knowm.xchange.bitbay.service.account.params;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/**
 * Bitbay specifc params for withdrawing fiat currency
 *
 * @author bryant_harris
 */
public class BitbayWithdrawFundsSwiftParams implements WithdrawFundsParams {
  public final Currency currency;

  public final BigDecimal amount;

  public final String account;

  public final boolean express;

  public final String bic;

  public BitbayWithdrawFundsSwiftParams(
      Currency currency, BigDecimal amount, String account, boolean express, String bic) {
    this.currency = currency;
    this.amount = amount;
    this.account = account;
    this.express = express;
    this.bic = bic;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getAccount() {
    return account;
  }

  public boolean isExpress() {
    return express;
  }

  public String getBic() {
    return bic;
  }

  @Override
  public String toString() {
    return "BitbayWithdrawFundsSwift [currency="
        + currency
        + ", amount="
        + amount
        + ", account="
        + account
        + ", express="
        + express
        + ", bic="
        + bic
        + "]";
  }
}
