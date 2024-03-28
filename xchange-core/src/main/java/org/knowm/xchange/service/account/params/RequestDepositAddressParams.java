package org.knowm.xchange.service.account.params;

import org.knowm.xchange.currency.Currency;

public interface RequestDepositAddressParams {
  Currency getCurrency();

  String getNetwork();

  boolean isNewAddress();

  String[] getExtraArguments();
}
