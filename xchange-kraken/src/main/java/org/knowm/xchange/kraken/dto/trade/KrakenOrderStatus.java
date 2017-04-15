package org.knowm.xchange.kraken.dto.trade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.kraken.dto.trade.KrakenOrderStatus.KrakenOrderStatusDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = KrakenOrderStatusDeserializer.class)
public enum KrakenOrderStatus {

  PENDING, OPEN, CLOSED, CANCELED, EXPIRED;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static KrakenOrderStatus fromString(String orderStatusString) {

    return fromString.get(orderStatusString.toLowerCase());
  }

  private static final Map<String, KrakenOrderStatus> fromString = new HashMap<>();

  static {
    for (KrakenOrderStatus orderStatus : values())
      fromString.put(orderStatus.toString(), orderStatus);
  }

  static class KrakenOrderStatusDeserializer extends JsonDeserializer<KrakenOrderStatus> {

    @Override
    public KrakenOrderStatus deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderStatusString = node.textValue();
      return fromString(orderStatusString);
    }
  }
}