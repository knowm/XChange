package org.knowm.xchange.coincheck.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;

public class CoincheckPairSerializer extends JsonSerializer<CoincheckPair> {
  @Override
  public void serialize(CoincheckPair value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(CoincheckPair.pairToString(value));
  }
}
