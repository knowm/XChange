package org.knowm.xchange.service.marketdata.params;

import java.util.Collection;
import org.knowm.xchange.instrument.Instrument;

public interface InstrumentsParams extends Params{
  Collection<Instrument> getInstruments();
}
