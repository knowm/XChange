package org.knowm.xchange.bitget.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

/** Converts string to {@code Instrument} */
public class StringToInstrumentConverter extends StdConverter<String, Instrument> {

  @Override
  public Instrument convert(String value) {
    CurrencyPair currencyPair = BitgetAdapters.toCurrencyPair(value);
    if (currencyPair != null) {
      return new FuturesContract(currencyPair, "PERP");
    }

    return null;
  }
}
