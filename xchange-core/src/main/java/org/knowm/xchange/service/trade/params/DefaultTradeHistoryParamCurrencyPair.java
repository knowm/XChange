package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultTradeHistoryParamCurrencyPair implements TradeHistoryParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultTradeHistoryParamCurrencyPair() {}

  public DefaultTradeHistoryParamCurrencyPair(CurrencyPair pair) {
    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }
}
