package org.knowm.xchange.service.trade.params.orders;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public class DefaultOpenOrdersParamInstrument implements OpenOrdersParamInstrument {

  private Instrument instrument;

  public DefaultOpenOrdersParamInstrument() {}

  public DefaultOpenOrdersParamInstrument(CurrencyPair instrument) {
    this.instrument = instrument;
  }

  public static List<Instrument> getInstruments(OpenOrdersParams params, Exchange exchange) {
    List<Instrument> instruments = new ArrayList<>();
    if (params instanceof OpenOrdersParamInstrument) {
      final Instrument paramsInst = ((OpenOrdersParamInstrument) params).getInstrument();
      if (paramsInst != null) {
        instruments.add(paramsInst);
      }
    }
    if (instruments.isEmpty()) {
      instruments = exchange.getExchangeInstruments();
    }
    return instruments;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }

  @Override
  public void setInstrument(final Instrument instrument) {
    this.instrument = instrument;
  }

  @Override
  public String toString() {
    return String.format("DefaultOpenOrdersParamInstrument{%s}", instrument);
  }
}
