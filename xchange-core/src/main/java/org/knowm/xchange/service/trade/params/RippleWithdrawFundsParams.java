package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.knowm.xchange.currency.Currency;

/**
 * this can be also used for XLM withdrawal, destination tag will be used as memo_id api param
 */
public class RippleWithdrawFundsParams extends DefaultWithdrawFundsParams {
  @Nullable public final String tag; // optional

  public RippleWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public RippleWithdrawFundsParams(
      String address, Currency currency, BigDecimal amount, String tag) {
    super(address, currency, amount);
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "RippleWithdrawFundsParams{"
        + "address='"
        + getAddress()
        + '\''
        + ", tag='"
        + getTag()
        + '\''
        + ", currency="
        + getCurrency()
        + ", amount="
        + getAmount()
        + ", commission="
        + getCommission()
        + '}';
  }

  @Nullable
  public String getTag() {
    return tag;
  }
}
