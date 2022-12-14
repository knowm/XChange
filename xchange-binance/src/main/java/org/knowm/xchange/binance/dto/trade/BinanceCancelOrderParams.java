package org.knowm.xchange.binance.dto.trade;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;

public class BinanceCancelOrderParams implements CancelOrderByIdParams, CancelOrderByInstrument {
  private final String orderId;
  private final Instrument pair;

  public BinanceCancelOrderParams(Instrument pair, String orderId) {
    this.pair = pair;
    this.orderId = orderId;
  }

  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }
}
