package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CurrencyPairParam;

public interface OrderQueryParamCurrencyPair extends OrderQueryParams, CurrencyPairParam {
  CurrencyPair getCurrencyPair();

  void setCurrencyPair(CurrencyPair currencyPair);
}
