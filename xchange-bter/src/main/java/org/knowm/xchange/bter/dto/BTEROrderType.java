package org.knowm.xchange.bter.dto;

import java.io.IOException;

import org.knowm.xchange.bter.dto.BTEROrderType.BTEROrderTypeDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BTEROrderTypeDeserializer.class)
public enum BTEROrderType {

  BUY, SELL;

  static class BTEROrderTypeDeserializer extends JsonDeserializer<BTEROrderType> {

    @Override
    public BTEROrderType deserialize(JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String orderType = node.asText();
      return BTEROrderType.valueOf(orderType.toUpperCase());
    }
  }
}
