package org.knowm.xchange.liqui.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.dto.Order;

@JsonDeserialize(using = LiquiTradeType.TradeTypeDeserializer.class)
public enum LiquiTradeType {
  BUY,
  SELL;

  private static final Map<String, LiquiTradeType> fromString = new HashMap<>();

  static {
    for (final LiquiTradeType type : values()) fromString.put(type.toString(), type);

    fromString.put("bid", BUY);
    fromString.put("ask", SELL);
    fromString.put("buy", BUY);
    fromString.put("sell", SELL);
  }

  public static LiquiTradeType fromString(final String typeString) {

    return fromString.get(typeString.toLowerCase());
  }

  public static LiquiTradeType fromOrderType(final Order.OrderType type) {

    return type == Order.OrderType.ASK ? LiquiTradeType.SELL : LiquiTradeType.BUY;
  }

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  static class TradeTypeDeserializer extends JsonDeserializer<LiquiTradeType> {

    @Override
    public LiquiTradeType deserialize(
        final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String typeString = node.textValue();
      return fromString(typeString);
    }
  }
}
