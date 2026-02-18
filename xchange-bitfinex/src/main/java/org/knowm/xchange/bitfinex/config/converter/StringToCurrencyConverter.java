package org.knowm.xchange.bitfinex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.currency.Currency;

/** Converts string value to {@code Currency} */
public class StringToCurrencyConverter extends StdConverter<String, Currency> {

  @Override
  public Currency convert(String value) {
    return BitfinexAdapters.toCurrency(value);
  }
}
