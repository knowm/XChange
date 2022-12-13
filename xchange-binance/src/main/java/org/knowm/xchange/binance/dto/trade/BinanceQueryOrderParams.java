package org.knowm.xchange.binance.dto.trade;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;

public class BinanceQueryOrderParams implements OrderQueryParamInstrument {
  private String orderId;
  private Instrument instrument;

  public BinanceQueryOrderParams() {}

  public BinanceQueryOrderParams(Instrument instrument, String orderId) {
    this.instrument = instrument;
    this.orderId = orderId;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
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
