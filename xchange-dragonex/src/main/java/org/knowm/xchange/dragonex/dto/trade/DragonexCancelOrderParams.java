package org.knowm.xchange.dragonex.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

public class DragonexCancelOrderParams implements CancelOrderByCurrencyPair, CancelOrderByIdParams {

  private CurrencyPair currencyPair;
  private String orderId;

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
