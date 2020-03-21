package org.knowm.xchange.service.trade.params.orders;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public class DefaultOpenOrdersParamInstrument implements OpenOrdersParamInstrument {

  private Instrument instrument;

  public DefaultOpenOrdersParamInstrument() {}

  public DefaultOpenOrdersParamInstrument(Instrument instrument) {

    this.instrument = instrument;
  }

  public static List<Instrument> getInstruments(OpenOrdersParams params, Exchange exchange) {

    List<Instrument> instruments = new ArrayList<>();
    if (params instanceof OpenOrdersParamInstrument) {
      final Instrument paramsCp = ((OpenOrdersParamInstrument) params).getInstrument();
      if (paramsCp != null) {
        instruments.add(paramsCp);
      }
    }
    if (instruments.isEmpty()) {
      instruments = exchange.getExchangeSymbols();
    }
    return instruments;
  }

  /**
   * @deprecated CurrencyPair is a subtype of Instrument <br>
   *     use {@link #getInstrument()} instead like this:
   *     <blockquote>
   *     <pre>
   * getInstrument()
   * </pre>
   *     </blockquote>
   */
  @Deprecated
  public static List<CurrencyPair> getPairs(OpenOrdersParams params, Exchange exchange) {
    List<CurrencyPair> pairs = new ArrayList<>();
    if (params instanceof OpenOrdersParamInstrument) {
      final Instrument paramsCp = ((OpenOrdersParamInstrument) params).getInstrument();
      if (paramsCp != null) {
        if (paramsCp instanceof CurrencyPair) pairs.add((CurrencyPair) paramsCp);
      }
    }
    if (pairs.isEmpty()) {
      for (Instrument instrument : exchange.getExchangeSymbols()) {
        if (instrument instanceof CurrencyPair) pairs.add((CurrencyPair) instrument);
      }
    }
    return pairs;
  }

  @Override
  public Instrument getInstrument() {

    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {

    this.instrument = instrument;
  }

  @Override
  public String toString() {

    return String.format("DefaultOpenOrdersParamInstrument{%s}", instrument);
  }
}
