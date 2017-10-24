package org.knowm.xchange.service.trade.params;

import java.util.Collection;

import org.knowm.xchange.currency.CurrencyPair;

public interface TradeHistoryParamMultiCurrencyPair extends TradeHistoryParams {

  void setCurrencyPairs(Collection<CurrencyPair> pairs);

  Collection<CurrencyPair> getCurrencyPairs();
}
