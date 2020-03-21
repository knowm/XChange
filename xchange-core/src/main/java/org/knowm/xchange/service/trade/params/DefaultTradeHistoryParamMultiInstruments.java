package org.knowm.xchange.service.trade.params;

import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.instrument.Instrument;

public class DefaultTradeHistoryParamMultiInstruments implements TradeHistoryParamMultiInstrument {

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
