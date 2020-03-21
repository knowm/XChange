package org.knowm.xchange.instrument;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;

public class InstrumentDeserializer extends KeyDeserializer {

  @Override
  public Instrument deserializeKey(String key, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    long count = key.chars().filter(ch -> ch == '/').count();

    if (count == 1) return new CurrencyPair(key);
    else return null;
  }
}
