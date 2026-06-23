package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;

public class StringToFutureContractConverter extends StdConverter<String, Instrument> {
  @Override
  public Instrument convert(String value) {
    return new FuturesContract(new CurrencyPair(value.replace('_', '/')) + "/PERP");
  }
}
