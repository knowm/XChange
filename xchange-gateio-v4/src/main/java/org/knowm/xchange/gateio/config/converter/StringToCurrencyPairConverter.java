package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Converts string to {@code CurrencyPair}
 */
public class StringToCurrencyPairConverter extends StdConverter<String, CurrencyPair> {

  @Override
  public CurrencyPair convert(String value) {
    return new CurrencyPair(value.replace('_', '/'));
  }
}
