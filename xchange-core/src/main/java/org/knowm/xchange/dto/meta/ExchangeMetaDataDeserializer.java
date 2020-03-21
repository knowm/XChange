package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Map;

public class ExchangeMetaDataDeserializer extends StdDeserializer<ExchangeMetaData> {

  public ExchangeMetaDataDeserializer() {
    this(null);
  }

  public ExchangeMetaDataDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public ExchangeMetaData deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    // JsonNode node = jp.getCodec().readTree(jp);
    JsonDeserializer<Object> deserializer =
        ctxt.findRootValueDeserializer(ctxt.constructType(Map.class));
    Map<String, Object> map = (Map<String, Object>) deserializer.deserialize(jp, ctxt);
    Map<String, Object> instruments = (Map<String, Object>) map.get("instruments");
    return new ExchangeMetaData(null, null, null, null, null);
  }
}
