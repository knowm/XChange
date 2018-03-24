package org.knowm.xchange.yobit.dto;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultTickerRequestParams extends MultiCurrencyPairTickersRequestParams {

  public DefaultTickerRequestParams(CurrencyPair currencyPair) {
    super(currencyPair);
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPairs.iterator().next();
  }
}
