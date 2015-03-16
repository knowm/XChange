package com.xeiam.xchange.service.polling.trade.params;

import com.xeiam.xchange.currency.CurrencyPair;

public class DefaultTradeHistoryParamCurrencyPair implements TradeHistoryParamCurrencyPair {

  private CurrencyPair pair;

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }
}
