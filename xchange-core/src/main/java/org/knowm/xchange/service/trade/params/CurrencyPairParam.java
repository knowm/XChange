package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;

public interface CurrencyPairParam {
  CurrencyPair getCurrencyPair();

  void setCurrencyPair(CurrencyPair pair);
}
