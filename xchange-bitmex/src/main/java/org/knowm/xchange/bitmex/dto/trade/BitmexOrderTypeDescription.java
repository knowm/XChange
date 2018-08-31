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
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderTypeDescription.BitmexOrderTypeDeserializer;

@JsonDeserialize(using = BitmexOrderTypeDeserializer.class)
public enum BitmexOrderTypeDescription {
  MARKET,
  LIMIT,
  STOP_LOSS,
  TAKE_PROFIT,
  STOP_LOSS_PROFIT,
  STOP_LOSS_PROFIT_LIMIT,
  STOP_LOSS_LIMIT,
  TAKE_PROFIT_LIMIT,
  TRAILING_STOP,
  TRAILING_STOP_LIMIT,
  STOP_LOSS_AND_LIMIT,
  SETTLE_POSITION;

  private static final Map<String, BitmexOrderTypeDescription> fromString = new HashMap<>();

  static {
    for (BitmexOrderTypeDescription orderType : values())
      fromString.put(orderType.toString(), orderType);

    fromString.put("l", LIMIT);
    fromString.put("m", MARKET);
  }

  public static BitmexOrderTypeDescription fromString(String orderTypeString) {

    return fromString.get(orderTypeString.replace('-', '_').toLowerCase());
  }

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public String toApiFormat() {

    return name().toLowerCase().replace('_', '-');
  }

  static class BitmexOrderTypeDeserializer extends JsonDeserializer<BitmexOrderTypeDescription> {

    @Override
    public BitmexOrderTypeDescription deserialize(
        JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderTypeString = node.textValue();
      return fromString(orderTypeString);
    }
  }
}
