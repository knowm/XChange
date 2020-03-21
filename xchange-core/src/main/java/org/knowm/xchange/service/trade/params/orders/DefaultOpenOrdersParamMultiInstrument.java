package org.knowm.xchange.service.trade.params.orders;

import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.instrument.Instrument;

public class DefaultOpenOrdersParamMultiInstrument implements OpenOrdersParamMultiInstrument {

  private Collection<Instrument> instruments = Collections.emptySet();

  @Override
  public Collection<Instrument> getInstruments() {
    return instruments;
  }

  @Override
  public void setInstruments(Collection<Instrument> value) {
    instruments = value;
  }
}
