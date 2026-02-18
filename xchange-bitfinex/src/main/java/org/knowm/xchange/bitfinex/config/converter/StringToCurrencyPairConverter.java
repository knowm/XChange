package org.knowm.xchange.bitfinex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.currency.CurrencyPair;

/** Converts string value to {@code Currency} */
public class StringToCurrencyPairConverter extends StdConverter<String, CurrencyPair> {

  @Override
  public CurrencyPair convert(String value) {
    return BitfinexAdapters.adaptCurrencyPair(value);
  }
}
