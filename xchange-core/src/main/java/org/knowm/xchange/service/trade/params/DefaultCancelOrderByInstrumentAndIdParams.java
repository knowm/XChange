package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.instrument.Instrument;

public class DefaultCancelOrderByInstrumentAndIdParams
    implements CancelOrderByIdParams, CancelOrderByInstrument {
  private Instrument instrument;
  private String orderId;

  public DefaultCancelOrderByInstrumentAndIdParams(Instrument instrument, String orderId) {
    this.instrument = instrument;
    this.orderId = orderId;
  }

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }
}
