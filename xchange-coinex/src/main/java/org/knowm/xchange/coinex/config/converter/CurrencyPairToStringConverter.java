package org.knowm.xchange.coinex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Converts {@code CurrencyPair} to string
 */
public class CurrencyPairToStringConverter extends StdConverter<CurrencyPair, String> {

  @Override
  public String convert(CurrencyPair value) {
    return CoinexAdapters.toString(value);
  }
}
