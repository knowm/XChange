package org.knowm.xchange.service.trade.params;

import java.util.Collection;
import org.knowm.xchange.currency.CurrencyPair;

public interface TradeHistoryParamMultiCurrencyPair extends TradeHistoryParams {

  Collection<CurrencyPair> getCurrencyPairs();

  void setCurrencyPairs(Collection<CurrencyPair> pairs);
}
