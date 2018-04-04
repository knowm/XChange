package org.knowm.xchange.currency;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/** @author timmolter */
public class CustomCurrencyPairSerializer extends JsonSerializer<CurrencyPair> {

  @Override
  public void serialize(
      CurrencyPair currencyPair, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {

    //    jsonGenerator.writeStartObject();
    jsonGenerator.writeString(currencyPair.toString());
    //    jsonGenerator.writeEndObject();

  }
}
