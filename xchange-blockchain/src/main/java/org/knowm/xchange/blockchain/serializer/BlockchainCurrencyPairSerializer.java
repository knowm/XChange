package org.knowm.xchange.blockchain.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.currency.CurrencyPair;

public class BlockchainCurrencyPairSerializer extends JsonSerializer<CurrencyPair> {

  @Override
  public void serialize(
      CurrencyPair currencyPair, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeString(BlockchainAdapters.toSymbol(currencyPair));
  }
}
