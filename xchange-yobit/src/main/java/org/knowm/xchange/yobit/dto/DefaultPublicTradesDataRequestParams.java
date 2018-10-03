package org.knowm.xchange.yobit.dto;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultPublicTradesDataRequestParams
    extends MultiCurrencyPublicTradesDataRequestParams {

  public DefaultPublicTradesDataRequestParams(CurrencyPair currencyPair) {
    super(currencyPair);
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPairs.iterator().next();
  }
}
