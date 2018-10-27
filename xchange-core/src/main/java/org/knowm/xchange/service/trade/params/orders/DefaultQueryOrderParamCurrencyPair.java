package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultQueryOrderParamCurrencyPair extends DefaultQueryOrderParam
    implements OrderQueryParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultQueryOrderParamCurrencyPair() {
    super();
  }

  public DefaultQueryOrderParamCurrencyPair(CurrencyPair pair, String orderId) {
    super(orderId);
    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    pair = currencyPair;
  }
}
