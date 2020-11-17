package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.instrument.Instrument;

public class DefaultTradeHistoryParamInstrument implements TradeHistoryParamInstrument {

  private Instrument instrument;

  public DefaultTradeHistoryParamInstrument() {}

  public DefaultTradeHistoryParamInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(final Instrument instrument) {
    this.instrument = instrument;
  }
}
