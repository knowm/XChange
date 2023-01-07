package org.knowm.xchange.livecoin.dto.marketdata;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LivecoinOrdersDeserializer extends JsonDeserializer<List<LivecoinOrder>> {

  @Override
  public List<LivecoinOrder> deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode array = jsonParser.readValueAsTree();
    if (array.isNull()) {
      return Collections.EMPTY_LIST;
    }
    if (!array.isArray()) {
      throw new JsonParseException(array.traverse(), "Expected array");
    }
    List<LivecoinOrder> result = new ArrayList<>(array.size());
    for (int i = 0; i < array.size(); ++i) {
      JsonNode order = array.get(i);
      if (!order.isArray() || order.size() < 2) {
        throw new JsonParseException(order.traverse(), "Expected array of at least two elements");
      }
      BigDecimal rate = toBigDecimal(order.get(0));
      BigDecimal quantity = toBigDecimal(order.get(1));
      result.add(new LivecoinOrder(rate, quantity));
    }
    return result;
  }

  private BigDecimal toBigDecimal(JsonNode elem) throws JsonProcessingException {
    try {
      return new BigDecimal(elem.asText());
    } catch (NumberFormatException e) {
      throw new JsonParseException(elem.traverse(), "Expected decimal", e);
    }
  }
}
