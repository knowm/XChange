package org.knowm.xchange.service.marketdata.params;

import java.util.Collection;
import org.knowm.xchange.instrument.Instrument;

public interface InstrumentsParams {
  Collection<Instrument> getInstruments();
}
