package org.knowm.xchange.yobit.dto;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultOrderBookRequestParams extends MultiCurrencyOrderBooksRequestParams {
  public DefaultOrderBookRequestParams(CurrencyPair currencyPair) {
    this(null, currencyPair);
  }

  public DefaultOrderBookRequestParams(Integer desiredDepth, CurrencyPair currencyPair) {
    super(desiredDepth, currencyPair);
  }

  public CurrencyPair currencyPair() {
    return currencyPairs.iterator().next();
  }
}
