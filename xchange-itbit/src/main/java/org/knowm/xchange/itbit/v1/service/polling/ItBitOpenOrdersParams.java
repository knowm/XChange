package org.knowm.xchange.itbit.v1.service.polling;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.polling.trade.params.orders.OpenOrdersParamCurrencyPair;

public class ItBitOpenOrdersParams implements OpenOrdersParamCurrencyPair {
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
