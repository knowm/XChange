package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultCancelOrderByCurrencyPairAndIdParams
    implements CancelOrderByIdParams, CancelOrderByCurrencyPair {

  private CurrencyPair currencyPair;
  private String orderId;

  public DefaultCancelOrderByCurrencyPairAndIdParams(CurrencyPair currencyPair, String orderId) {
    this.currencyPair = currencyPair;
    this.orderId = orderId;
  }

  public void setCurrencyPair(CurrencyPair pair) {
    this.currencyPair = pair;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }
}
