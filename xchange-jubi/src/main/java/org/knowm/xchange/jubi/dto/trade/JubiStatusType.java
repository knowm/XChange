package org.knowm.xchange.jubi.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzf on 2017/7/18.
 */
public enum JubiStatusType {
  New, Open, Cancelled, Closed;

  private static final Map<String, JubiStatusType> fromString = new HashMap<>();

  static {
    for (JubiStatusType jubiStatusType : values()) {
      fromString.put(jubiStatusType.name().toLowerCase(), jubiStatusType);
    }
  }

  public static JubiStatusType fromString(String jubiStatusTypeString) {
    return fromString.get(jubiStatusTypeString.toLowerCase());
  }

  static class JubiStatusTypeDeserializer extends JsonDeserializer<JubiStatusType> {
    @Override
    public JubiStatusType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String jubiStatusType = node.textValue();
      return fromString(jubiStatusType);
    }
  }
}
