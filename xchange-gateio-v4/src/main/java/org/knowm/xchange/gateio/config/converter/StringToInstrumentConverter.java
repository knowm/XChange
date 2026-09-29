package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

public class StringToInstrumentConverter extends StdConverter<String, Instrument> {
  @Override
  public Instrument convert(String value) {
    if (value.contains("PERP"))
      return new FuturesContract(new CurrencyPair(value.replace('_', '/')) + "/PERP");
    else
      return new CurrencyPair(value.replace('_', '/'));
  }
}

