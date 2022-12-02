package org.knowm.xchange.okcoin.v3.dto.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class StringNumeralBooleanDeserializer extends JsonDeserializer<Boolean> {
  @Override
  public Boolean deserialize(JsonParser parser, DeserializationContext context)
      throws IOException, JsonProcessingException {
    return !"0".equals(parser.getText());
  }
}
