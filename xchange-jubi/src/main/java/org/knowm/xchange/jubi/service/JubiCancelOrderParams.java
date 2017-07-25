package org.knowm.xchange.jubi.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

import java.math.BigDecimal;

/**
 * Created by Dzf on 2017/7/24.
 */
public class JubiCancelOrderParams implements CancelOrderParams {
  private final CurrencyPair currencyPair;
  private final BigDecimal orderId;

  public JubiCancelOrderParams(CurrencyPair currencyPair, BigDecimal orderId) {
    this.currencyPair = currencyPair;
    this.orderId = orderId;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getOrderId() {
    return orderId;
  }
}
