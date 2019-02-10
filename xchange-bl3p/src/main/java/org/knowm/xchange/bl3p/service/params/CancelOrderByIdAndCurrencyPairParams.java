package org.knowm.xchange.bl3p.service.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

public class CancelOrderByIdAndCurrencyPairParams
    implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
  private CurrencyPair currencyPair;
  private String orderId;

  public CancelOrderByIdAndCurrencyPairParams(CurrencyPair currencyPair, String orderId) {
    this.currencyPair = currencyPair;
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
