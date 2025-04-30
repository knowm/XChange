package info.bitrich.xchangestream.bitmex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import info.bitrich.xchangestream.bitmex.BitmexStreamingAdapters;
import org.knowm.xchange.currency.CurrencyPair;

/** Converts string to {@code CurrencyPair} */
public class StringToCurrencyPairConverter extends StdConverter<String, CurrencyPair> {

  @Override
  public CurrencyPair convert(String value) {
    return BitmexStreamingAdapters.toCurrencyPair(value);
  }
}
