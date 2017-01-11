package org.knowm.xchange.therock.service.polling;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.polling.trade.params.orders.OpenOrdersParamCurrencyPair;

public class TheRockOpenOrdersParams implements OpenOrdersParamCurrencyPair {
  private CurrencyPair currencyPair;

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
    this.currencyPair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
