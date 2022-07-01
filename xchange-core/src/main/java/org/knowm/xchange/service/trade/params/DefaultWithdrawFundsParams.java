package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AddressWithTag;

public class DefaultWithdrawFundsParams implements WithdrawFundsParams {
  public final String address;

  private final String addressTag;

  public final Currency currency;

  public final BigDecimal amount;

  @Nullable public final BigDecimal commission;

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public DefaultWithdrawFundsParams(AddressWithTag address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public DefaultWithdrawFundsParams(
      String address, Currency currency, BigDecimal amount, BigDecimal commission) {
    this.address = address;
    this.addressTag = null;
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  public DefaultWithdrawFundsParams(
      AddressWithTag address, Currency currency, BigDecimal amount, BigDecimal commission) {
    this.address = address.getAddress();
    this.addressTag = address.getAddressTag();
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  public DefaultWithdrawFundsParams(
      String address,
      String addressTag,
      Currency currency,
      BigDecimal amount,
      BigDecimal commission) {
    this.address = address;
    this.addressTag = addressTag;
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  public String getAddress() {
    return address;
  }

  public String getAddressTag() {
    return addressTag;
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
        + ", addressTag="
        + getAddressTag()
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
