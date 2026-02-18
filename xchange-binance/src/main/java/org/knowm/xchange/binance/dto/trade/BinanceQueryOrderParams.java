package org.knowm.xchange.binance.dto.trade;

import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;

@Getter
@Setter
public class BinanceQueryOrderParams implements OrderQueryParamInstrument {
  private String orderId;
  private Instrument instrument;

  public BinanceQueryOrderParams() {}

  public BinanceQueryOrderParams(Instrument instrument, String orderId) {
    this.instrument = instrument;
    this.orderId = orderId;
  }
}
