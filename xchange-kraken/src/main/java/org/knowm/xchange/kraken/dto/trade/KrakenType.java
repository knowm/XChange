package org.knowm.xchange.kraken.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.kraken.dto.trade.KrakenType.KrakenTypeDeserializer;

@JsonDeserialize(using = KrakenTypeDeserializer.class)
public enum KrakenType {
  BUY,
  SELL;

  private static final Map<String, KrakenType> fromString = new HashMap<>();

  static {
    for (KrakenType type : values()) fromString.put(type.toString(), type);

    fromString.put("b", BUY);
    fromString.put("s", SELL);
  }

  public static KrakenType fromString(String typeString) {

    return fromString.get(typeString.toLowerCase());
  }

  public static KrakenType fromOrderType(OrderType type) {

    return type == OrderType.ASK ? KrakenType.SELL : KrakenType.BUY;
  }

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  static class KrakenTypeDeserializer extends JsonDeserializer<KrakenType> {

    @Override
    public KrakenType deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String typeString = node.textValue();
      return fromString(typeString);
    }
  }
}
