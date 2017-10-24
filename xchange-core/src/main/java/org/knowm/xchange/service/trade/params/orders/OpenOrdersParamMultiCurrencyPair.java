package org.knowm.xchange.service.trade.params.orders;

import java.util.Collection;

import org.knowm.xchange.currency.CurrencyPair;

public interface OpenOrdersParamMultiCurrencyPair extends OpenOrdersParams {

  void setCurrencyPairs(Collection<CurrencyPair> pairs);

  Collection<CurrencyPair> getCurrencyPairs();
}
