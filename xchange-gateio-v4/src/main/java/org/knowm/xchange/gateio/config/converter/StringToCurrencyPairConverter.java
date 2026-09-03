package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

/**
 * Converts string to {@code Instrument}
 */
public class StringToCurrencyPairConverter extends StdConverter<String, Instrument> {

  @Override
  public Instrument convert(String value) {
    return new CurrencyPair(value.replace('_', '/'));
  }
}
