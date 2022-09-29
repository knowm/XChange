package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

public class BitrueCancelOrderParams implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
  private final String orderId;
  private final CurrencyPair pair;

  public BitrueCancelOrderParams(CurrencyPair pair, String orderId) {
    this.pair = pair;
    this.orderId = orderId;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }
}
