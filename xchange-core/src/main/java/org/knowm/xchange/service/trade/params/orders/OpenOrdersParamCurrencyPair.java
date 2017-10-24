package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;

public interface OpenOrdersParamCurrencyPair extends OpenOrdersParams {
  void setCurrencyPair(CurrencyPair pair);

  CurrencyPair getCurrencyPair();
}
