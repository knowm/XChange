package org.knowm.xchange.cexio.dto.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.cexio.dto.trade.CexIOOpenOrders.CexIOOpenOrdersDeserializer;
import org.knowm.xchange.cexio.dto.trade.CexIOOrder.Type;
import org.knowm.xchange.exceptions.ExchangeException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CexIOOpenOrdersDeserializer.class)
public class CexIOOpenOrders {

  private final List<CexIOOrder> openOrders;

  public CexIOOpenOrders(List<CexIOOrder> openOrders) {

    this.openOrders = openOrders;
  }

  public CexIOOpenOrders() {

    this.openOrders = new ArrayList<>();
  }

  public List<CexIOOrder> getOpenOrders() {

    return openOrders;
  }

  public void addOpenOrders(List<CexIOOrder> openOrders) {

    this.openOrders.addAll(openOrders);
  }

  public void addOpenOrder(CexIOOrder openOrder) {

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
        } else {
          throw new ExchangeException("Unable to retrieve open orders because " + errorText);
        }
      }

      final List<CexIOOrder> openOrders = new ArrayList<>();
      if (openOrdersNode.isArray()) {
        for (JsonNode openOrderNode : openOrdersNode) {
          final long id = openOrderNode.path("id").asLong();
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
