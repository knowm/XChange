package org.knowm.xchange.vircurex.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Created by David Henry on 2/20/14. */
public class VircurexOpenOrdersDeserializer extends JsonDeserializer<VircurexOpenOrdersReturn> {

  @Override
  public VircurexOpenOrdersReturn deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException {

    List<VircurexOpenOrder> openOrdersList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNodes = mapper.readTree(jsonParser);

    Iterator<Map.Entry<String, JsonNode>> jsonNodeIterator = jsonNodes.fields();

    while (jsonNodeIterator.hasNext()) {

      Map.Entry<String, JsonNode> jsonNodeField = jsonNodeIterator.next();

      if (jsonNodeField.getKey().contains("order-")) {
        VircurexOpenOrder openOrder =
            mapper.readValue(jsonNodeField.getValue().toString(), VircurexOpenOrder.class);
        openOrdersList.add(openOrder);
      } else {
        break; // found the last of the order objects
      }
    }

    VircurexOpenOrdersReturn openOrdersReturn =
        new VircurexOpenOrdersReturn(
            jsonNodes.get("numberorders").asInt(),
            jsonNodes.get("account").asText(),
            jsonNodes.get("timestamp").asText(),
            jsonNodes.get("token").asText(),
            jsonNodes.get("status").asInt(),
            jsonNodes.get("function").asText());

    if (openOrdersList.size() > 0) {
      openOrdersReturn.setOpenOrders(openOrdersList);
    }

    return openOrdersReturn;
  }
}
