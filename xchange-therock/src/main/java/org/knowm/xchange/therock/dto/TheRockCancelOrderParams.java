package org.knowm.xchange.therock.dto;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

public class TheRockCancelOrderParams implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
  public final CurrencyPair currencyPair;
  public final Long orderId;

  public TheRockCancelOrderParams(CurrencyPair currencyPair, Long orderId) {
    this.currencyPair = currencyPair;
    this.orderId = orderId;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public String getOrderId() {
    return orderId.toString();
  }
}
