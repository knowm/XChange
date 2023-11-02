package org.knowm.xchange.binance.dto.trade;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;

public class BinanceQueryOrderParams implements OrderQueryParamInstrument {
  private String orderId;
  private Instrument instrument;
  private Boolean isMarginOrder;

  public BinanceQueryOrderParams() {}

  public BinanceQueryOrderParams(Instrument instrument, String orderId) {
    this.instrument = instrument;
    this.orderId = orderId;
  }

  public BinanceQueryOrderParams(Instrument instrument, String orderId, Boolean isMarginOrder) {
    this.instrument = instrument;
    this.orderId = orderId;
    this.isMarginOrder=isMarginOrder;
  }

  public BinanceQueryOrderParams(Instrument instrument, Boolean isMarginOrder) {
    this.instrument = instrument;
    this.isMarginOrder=isMarginOrder;
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

  public Boolean getIsMarginOrder() {
    return isMarginOrder;
  }

  public void setIsMarginOrder(Boolean isMarginOrder) {
    this.isMarginOrder = isMarginOrder;
  }
}
