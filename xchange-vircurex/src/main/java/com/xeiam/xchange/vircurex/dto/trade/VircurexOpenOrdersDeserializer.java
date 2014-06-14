/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.vircurex.dto.trade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by David Henry on 2/20/14.
 */
public class VircurexOpenOrdersDeserializer extends JsonDeserializer<VircurexOpenOrdersReturn> {

  @Override
  public VircurexOpenOrdersReturn deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

    List<VircurexOpenOrder> openOrdersList = new ArrayList<VircurexOpenOrder>();
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNodes = mapper.readTree(jsonParser);

    Iterator<Map.Entry<String, JsonNode>> jsonNodeIterator = jsonNodes.fields();

    while (jsonNodeIterator.hasNext()) {

      Map.Entry<String, JsonNode> jsonNodeField = jsonNodeIterator.next();

      if (jsonNodeField.getKey().contains("order-")) {
        VircurexOpenOrder openOrder = mapper.readValue(jsonNodeField.getValue().toString(), VircurexOpenOrder.class);
        openOrdersList.add(openOrder);
      }
      else {
        break; // found the last of the order objects
      }
    }

    VircurexOpenOrdersReturn openOrdersReturn =
        new VircurexOpenOrdersReturn(jsonNodes.get("numberorders").asInt(), jsonNodes.get("account").asText(), jsonNodes.get("timestamp").asText(), jsonNodes.get("token").asText(), jsonNodes.get(
            "status").asInt(), jsonNodes.get("function").asText());

    if (openOrdersList.size() > 0) {
      openOrdersReturn.setOpenOrders(openOrdersList);
    }

    return openOrdersReturn;
  }
}
