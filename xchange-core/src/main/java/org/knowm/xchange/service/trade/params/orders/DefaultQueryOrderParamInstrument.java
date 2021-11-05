package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.instrument.Instrument;

public class DefaultQueryOrderParamInstrument extends DefaultQueryOrderParam
    implements OrderQueryParamInstrument {
  private Instrument instrument;

  public DefaultQueryOrderParamInstrument(Instrument instrument, String orderId) {
    super(orderId);
    this.instrument = instrument;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }
}
