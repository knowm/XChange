package org.knowm.xchange.jubi.dto.trade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by Dzf on 2017/7/18.
 */
public enum JubiOrderType {
  Buy, Sell;

  private static final Map<String, JubiOrderType> fromString = new HashMap<>();

  static {
    for (JubiOrderType jubiOrderType : values()) {
      fromString.put(jubiOrderType.name().toLowerCase(), jubiOrderType);
    }
  }

  public static JubiOrderType fromString(String jubiOrderTypeString) {
    return fromString.get(jubiOrderTypeString.toLowerCase());
  }

  static class JubiOrderTypeDeserializer extends JsonDeserializer<JubiOrderType> {
    @Override
    public JubiOrderType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String jubiOrderType = node.textValue();
      return fromString(jubiOrderType);
    }
  }

}
