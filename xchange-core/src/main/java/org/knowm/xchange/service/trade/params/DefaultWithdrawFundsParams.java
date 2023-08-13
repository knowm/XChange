package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AddressWithTag;

@Value
@NonFinal
public class DefaultWithdrawFundsParams implements WithdrawFundsParams {

  String address;

  String addressTag;

  Currency currency;

  BigDecimal amount;

  BigDecimal commission;

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public DefaultWithdrawFundsParams(AddressWithTag address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public DefaultWithdrawFundsParams(String address, Currency currency, BigDecimal amount,
      BigDecimal commission) {
    this.address = address;
    this.addressTag = null;
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  public DefaultWithdrawFundsParams(AddressWithTag address, Currency currency, BigDecimal amount,
      BigDecimal commission) {
    this.address = address.getAddress();
    this.addressTag = address.getAddressTag();
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

  public DefaultWithdrawFundsParams(String address, String addressTag, Currency currency,
      BigDecimal amount, BigDecimal commission) {
    this.address = address;
    this.addressTag = addressTag;
    this.currency = currency;
    this.amount = amount;
    this.commission = commission;
  }

}
