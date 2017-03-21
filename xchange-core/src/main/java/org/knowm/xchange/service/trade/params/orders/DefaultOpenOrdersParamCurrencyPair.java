package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultOpenOrdersParamCurrencyPair implements OpenOrdersParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultOpenOrdersParamCurrencyPair() {
  }

  public DefaultOpenOrdersParamCurrencyPair(CurrencyPair pair) {
    this.pair = pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }
}
