package com.xeiam.xchange.kraken.dto.trade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.kraken.dto.trade.KrakenType.KrakenTypeDeserializer;

@JsonDeserialize(using = KrakenTypeDeserializer.class)
public enum KrakenType {

  BUY, SELL;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static KrakenType fromString(final String typeString) {

    return fromString.get(typeString.toLowerCase());
  }

  public static KrakenType fromOrderType(OrderType type) {

    return type == OrderType.ASK ? KrakenType.SELL : KrakenType.BUY;
  }

  private static final Map<String, KrakenType> fromString = new HashMap<String, KrakenType>();
  static {
    for (KrakenType type : values())
      fromString.put(type.toString(), type);

    fromString.put("b", BUY);
    fromString.put("s", SELL);
  }

  static class KrakenTypeDeserializer extends JsonDeserializer<KrakenType> {

    @Override
    public KrakenType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String typeString = node.textValue();
      return fromString(typeString);
    }
  }
}
