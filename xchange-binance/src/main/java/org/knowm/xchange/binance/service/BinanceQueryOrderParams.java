package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.dto.trade.margin.MarginAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair;

public class BinanceQueryOrderParams implements OrderQueryParamCurrencyPair {
  private String orderId;
  private CurrencyPair pair;
  private MarginAccountType marginAccountType;

  public BinanceQueryOrderParams() {}

  public BinanceQueryOrderParams(CurrencyPair pair, String orderId) {
    this.pair = pair;
    this.orderId = orderId;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {
    this.pair = pair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  @Override
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public MarginAccountType getMarginAccountType() {
    return marginAccountType;
  }

  public void setMarginAccountType(MarginAccountType marginAccountType) {
    this.marginAccountType = marginAccountType;
  }
}
