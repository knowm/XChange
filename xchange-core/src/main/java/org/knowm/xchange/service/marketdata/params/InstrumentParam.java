package org.knowm.xchange.service.marketdata.params;

import java.util.ArrayList;
import java.util.Collection;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public interface InstrumentParam extends Params {

  Collection<Instrument> getInstruments();

  /**
   * @deprecated CurrencyPair is a subtype of Instrument <br>
   *     use {@link #getInstruments()} instead like this:
   *     <blockquote>
   *     <pre>
   * getInstruments()
   * </pre>
   *     </blockquote>
   */
  @Deprecated
  default Collection<CurrencyPair> getCurrencyPairs() {
    Collection<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (Instrument instrument : getInstruments())
      if (instrument instanceof CurrencyPair) currencyPairs.add((CurrencyPair) instrument);

    return currencyPairs;
  }
}
