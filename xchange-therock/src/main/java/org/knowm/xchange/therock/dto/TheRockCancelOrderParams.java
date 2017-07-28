package org.knowm.xchange.therock.dto;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class TheRockCancelOrderParams implements CancelOrderParams {
  public final CurrencyPair currencyPair;
  public final Long orderId;

  public TheRockCancelOrderParams(CurrencyPair currencyPair, Long orderId) {
    this.currencyPair = currencyPair;
    this.orderId = orderId;
  }
}
