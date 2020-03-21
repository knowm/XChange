package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.instrument.Instrument;

public class DefaultQueryOrderParamInstrument extends DefaultQueryOrderParam
    implements OrderQueryParamInstrument {

  private Instrument pair;

  public DefaultQueryOrderParamInstrument() {
    super();
  }

  public DefaultQueryOrderParamInstrument(Instrument pair, String orderId) {
    super(orderId);
    this.pair = pair;
  }

  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public void setInstrument(Instrument currencyPair) {
    pair = currencyPair;
  }
}
