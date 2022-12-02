package org.knowm.xchange.coincheck.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;

public class CoincheckPairDeserializer extends JsonDeserializer<CoincheckPair> {
  @Override
  public CoincheckPair deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
    return CoincheckPair.stringToPair(p.getValueAsString());
  }
}
