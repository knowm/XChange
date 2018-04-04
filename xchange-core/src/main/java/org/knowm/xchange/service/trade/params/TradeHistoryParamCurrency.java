package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.Currency;

public interface TradeHistoryParamCurrency extends TradeHistoryParams {

  Currency getCurrency();

  void setCurrency(Currency currency);
}
