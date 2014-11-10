package com.xeiam.xchange.service.polling.trade;

import com.xeiam.xchange.currency.CurrencyPair;

public interface TradeHistoryParamCurrencyPair {

  void setCurrencyPair(CurrencyPair pair);

  CurrencyPair getCurrencyPair();
}
