package org.knowm.xchange.bitmex.dto.trade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.bitmex.dto.trade.BitmexSide.BitmexTypeDeserializer;
import org.knowm.xchange.dto.Order.OrderType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BitmexTypeDeserializer.class)
public enum BitmexSide {

  BUY, SELL;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static BitmexSide fromString(String typeString) {

    return fromString.get(typeString.toLowerCase());
  }

  public static BitmexSide fromOrderType(OrderType type) {

    return type == OrderType.ASK ? BitmexSide.SELL : BitmexSide.BUY;
  }

  private static final Map<String, BitmexSide> fromString = new HashMap<>();

  static {
    for (BitmexSide type : values())
      fromString.put(type.toString(), type);

    fromString.put("buy", BUY);
    fromString.put("sell", SELL);
  }

  static class BitmexTypeDeserializer extends JsonDeserializer<BitmexSide> {

    @Override
    public BitmexSide deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String typeString = node.textValue();
      return fromString(typeString);
    }
  }
}
