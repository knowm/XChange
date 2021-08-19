package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.instrument.Instrument;

public interface InstrumentParam {
  Instrument getInstrument();

  void setInstrument(Instrument instrument);
}
