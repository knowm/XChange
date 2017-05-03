package org.knowm.xchange.kraken.dto.trade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.kraken.dto.trade.KrakenOrderType.KrakenOrderTypeDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = KrakenOrderTypeDeserializer.class)
public enum KrakenOrderType {

  MARKET, LIMIT, STOP_LOSS, TAKE_PROFIT, STOP_LOSS_PROFIT, STOP_LOSS_PROFIT_LIMIT, STOP_LOSS_LIMIT, TAKE_PROFIT_LIMIT, TRAILING_STOP, TRAILING_STOP_LIMIT, STOP_LOSS_AND_LIMIT;

  @Override
  public String toString() {

    return super.toString().toLowerCase();
  }

  public static KrakenOrderType fromString(String orderTypeString) {

    return fromString.get(orderTypeString.replace('-', '_').toLowerCase());
  }

  public String toApiFormat() {

    return name().toLowerCase().replace('_', '-');
  }

  private static final Map<String, KrakenOrderType> fromString = new HashMap<>();

  static {
    for (KrakenOrderType orderType : values())
      fromString.put(orderType.toString(), orderType);

    fromString.put("l", LIMIT);
    fromString.put("m", MARKET);
  }

  static class KrakenOrderTypeDeserializer extends JsonDeserializer<KrakenOrderType> {

    @Override
    public KrakenOrderType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String orderTypeString = node.textValue();
      return fromString(orderTypeString);
    }
  }
}
