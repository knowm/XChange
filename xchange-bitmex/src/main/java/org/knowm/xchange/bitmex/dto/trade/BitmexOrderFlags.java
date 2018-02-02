package org.knowm.xchange.bitmex.dto.trade;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.knowm.xchange.dto.Order.IOrderFlags;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public enum BitmexOrderFlags implements IOrderFlags {

  FCIB, // prefer fee in base currency
  FCIQ, // prefer fee in quote currency
  NOMPP, // no market price protection
  POST, // for market maker orders
  VIQC; // volume in quote currency

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static BitmexOrderFlags fromString(String orderTypeString) {

    return fromString.get(orderTypeString.toLowerCase());
  }

  private static final Map<String, BitmexOrderFlags> fromString = new HashMap<>();

  static {
    for (BitmexOrderFlags orderFlag : values())
      fromString.put(orderFlag.toString(), orderFlag);
  }

  static class BitmexOrderFlagsDeserializer extends JsonDeserializer<Set<BitmexOrderFlags>> {

    @Override
    public Set<BitmexOrderFlags> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderFlagsString = node.textValue();
      Set<BitmexOrderFlags> orderFlags = EnumSet.noneOf(BitmexOrderFlags.class);
      if (!orderFlagsString.isEmpty()) {
        for (String orderFlag : orderFlagsString.split(","))
          orderFlags.add(fromString(orderFlag));
      }
      return orderFlags;
    }
  }
}
