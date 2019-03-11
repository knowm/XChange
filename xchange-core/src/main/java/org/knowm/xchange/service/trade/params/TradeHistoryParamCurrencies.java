package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.Currency;

public interface TradeHistoryParamCurrencies extends TradeHistoryParams {

  Currency[] getCurrencies();

  void setCurrencies(Currency[] currencies);
}
