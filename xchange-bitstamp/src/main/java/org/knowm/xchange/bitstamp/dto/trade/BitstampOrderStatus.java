package org.knowm.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum BitstampOrderStatus {
  Queue,
  Open,
  Canceled,
  Finished;

  private static final Map<String, BitstampOrderStatus> fromString =
      new HashMap<String, BitstampOrderStatus>();

  static {
    for (BitstampOrderStatus orderStatus : values()) {
      fromString.put(orderStatus.name().toLowerCase(), orderStatus);
    }
  }

  public static BitstampOrderStatus fromString(String orderStatusString) {
    return fromString.get(orderStatusString.toLowerCase());
  }

  static class BitstampOrderStatusDeserializer extends JsonDeserializer<BitstampOrderStatus> {

    @Override
    public BitstampOrderStatus deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderStatusString = node.textValue();
      return fromString(orderStatusString);
    }
  }
}
