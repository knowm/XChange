package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.Currency;

public interface TradeHistoryParamCurrencies extends TradeHistoryParams {

  void setCurrencies(Currency[] currencies);

  Currency[] getCurrencies();
}
