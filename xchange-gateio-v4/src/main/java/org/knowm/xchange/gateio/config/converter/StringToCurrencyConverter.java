package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.currency.Currency;

/**
 * Converts string value {@code Currency}
 */
public class StringToCurrencyConverter extends StdConverter<String, Currency> {

  @Override
  public Currency convert(String value) {
    return Currency.getInstance(value);
  }
}
