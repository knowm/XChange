package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.dto.trade.margin.MarginAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

public class BinanceCancelOrderParams implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
  private final String orderId;
  private final CurrencyPair pair;
  private MarginAccountType marginAccountType;

  public BinanceCancelOrderParams(CurrencyPair pair, String orderId) {
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

  public MarginAccountType getMarginAccountType() {
    return marginAccountType;
  }

  public void setMarginAccountType(MarginAccountType marginAccountType) {
    this.marginAccountType = marginAccountType;
  }
}
