package org.knowm.xchange.bitmex.dto.trade;

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
import org.knowm.xchange.bitmex.dto.trade.BitmexTickDirection.BitmexTickDirectionDeserializer;

@JsonDeserialize(using = BitmexTickDirectionDeserializer.class)
public enum BitmexTickDirection {
  MINUSTICK,
  PLUSTICK,
  ZEROPLUSTICK;

  private static final Map<String, BitmexTickDirection> fromString = new HashMap<>();

  static {
    for (BitmexTickDirection tickDirection : values())
      fromString.put(tickDirection.toString(), tickDirection);
  }

  public static BitmexTickDirection fromString(String tickDirectionString) {

    return fromString.get(tickDirectionString.toLowerCase());
  }

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  static class BitmexTickDirectionDeserializer extends JsonDeserializer<BitmexTickDirection> {

    @Override
    public BitmexTickDirection deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String tickDirectionString = node.textValue();
      return fromString(tickDirectionString);
    }
  }
}
