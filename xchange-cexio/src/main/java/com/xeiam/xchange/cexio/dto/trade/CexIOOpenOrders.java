/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.cexio.dto.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.cexio.dto.trade.CexIOOpenOrders.CexIOOpenOrdersDeserializer;
import com.xeiam.xchange.cexio.dto.trade.CexIOOrder.Type;

@JsonDeserialize(using = CexIOOpenOrdersDeserializer.class)
public class CexIOOpenOrders {

  private final List<CexIOOrder> openOrders;

  public CexIOOpenOrders(final List<CexIOOrder> openOrders) {

    this.openOrders = openOrders;
  }

  public CexIOOpenOrders() {

    this.openOrders = new ArrayList<CexIOOrder>();
  }

  public List<CexIOOrder> getOpenOrders() {

    return openOrders;
  }

  public void addOpenOrders(final List<CexIOOrder> openOrders) {

    this.openOrders.addAll(openOrders);
  }

  public void addOpenOrder(final CexIOOrder openOrder) {

    this.openOrders.add(openOrder);
  }

  @Override
  public String toString() {

    return "CexIOOpenOrders [openOrders=" + openOrders + "]";
  }

  static class CexIOOpenOrdersDeserializer extends JsonDeserializer<CexIOOpenOrders> {

    @Override
    public CexIOOpenOrders deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode openOrdersNode = oc.readTree(jp);

      final JsonNode errorNode = openOrdersNode.path("error");
      if (!errorNode.isMissingNode()) {
        final String errorText = errorNode.asText();
        if (errorText.equals("Invalid symbols pair")) {
          return new CexIOOpenOrders();
        }
        else {
          throw new ExchangeException("Unable to retrieve open orders because " + errorText);
        }
      }

      final List<CexIOOrder> openOrders = new ArrayList<CexIOOrder>();
      if (openOrdersNode.isArray()) {
        for (JsonNode openOrderNode : openOrdersNode) {
          final int id = openOrderNode.path("id").asInt();
          final long time = openOrderNode.path("time").asLong();
          final Type type = Type.valueOf(openOrderNode.path("type").asText());
          final BigDecimal price = new BigDecimal(openOrderNode.path("price").asText());
          final BigDecimal amount = new BigDecimal(openOrderNode.path("amount").asText());
          final BigDecimal pending = new BigDecimal(openOrderNode.path("pending").asText());

          openOrders.add(new CexIOOrder(id, time, type, price, amount, pending, null));
        }
      }
      return new CexIOOpenOrders(openOrders);
    }
  }
}
