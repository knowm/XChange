package com.xeiam.xchange.service.polling.trade;

import com.xeiam.xchange.currency.CurrencyPair;

public interface TradeHistoryParamCurrencyPair extends TradeHistoryParams {

  void setCurrencyPair(CurrencyPair pair);

  CurrencyPair getCurrencyPair();
}
