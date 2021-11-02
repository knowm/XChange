package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.InstrumentParam;

public interface OrderQueryParamInstrument extends OrderQueryParams, InstrumentParam {
  Instrument getInstrument();

  void setInstrument(Instrument instrument);
}
