package org.knowm.xchange.bitmex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.Currency;

/** Converts string value to {@code Currency} */
public class StringToCurrencyConverter extends StdConverter<String, Currency> {

  @Override
  public Currency convert(String value) {
    return BitmexAdapters.toCurrency(value);
  }
}
