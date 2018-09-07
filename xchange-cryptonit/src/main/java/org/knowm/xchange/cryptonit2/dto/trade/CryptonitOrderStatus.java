package org.knowm.xchange.cryptonit2.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum CryptonitOrderStatus {
  Queue,
  Open,
  Finished;

  private static final Map<String, CryptonitOrderStatus> fromString =
      new HashMap<String, CryptonitOrderStatus>();

  static {
    for (CryptonitOrderStatus orderStatus : values()) {
      fromString.put(orderStatus.name().toLowerCase(), orderStatus);
    }
  }

  public static CryptonitOrderStatus fromString(String orderStatusString) {
    return fromString.get(orderStatusString.toLowerCase());
  }

  static class CryptonitOrderStatusDeserializer extends JsonDeserializer<CryptonitOrderStatus> {

    @Override
    public CryptonitOrderStatus deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderStatusString = node.textValue();
      return fromString(orderStatusString);
    }
  }
}
