package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.Currency;

public interface TradeHistoryParamCurrency extends TradeHistoryParams {

  void setCurrency(Currency currency);

  Currency getCurrency();
}
