package org.knowm.xchange.binance.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;

public class BinanceQueryOrderParams implements OrderQueryParamInstrument {
  private String orderId;
  private Instrument pair;

  public BinanceQueryOrderParams() {}

  public BinanceQueryOrderParams(CurrencyPair pair, String orderId) {
    this.pair = pair;
    this.orderId = orderId;
  }

  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public void setInstrument(Instrument pair) {
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
}
