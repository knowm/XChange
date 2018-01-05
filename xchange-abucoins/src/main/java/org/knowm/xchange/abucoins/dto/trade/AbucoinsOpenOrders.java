package org.knowm.xchange.abucoins.dto.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.abucoins.dto.trade.AbucoinsOpenOrders.AbucoinsOpenOrdersDeserializer;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder.Type;
import org.knowm.xchange.exceptions.ExchangeException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AbucoinsOpenOrdersDeserializer.class)
public class AbucoinsOpenOrders {

  private final List<AbucoinsOrder> openOrders;

  public AbucoinsOpenOrders(List<AbucoinsOrder> openOrders) {

    this.openOrders = openOrders;
  }

  public AbucoinsOpenOrders() {

    this.openOrders = new ArrayList<>();
  }

  public List<AbucoinsOrder> getOpenOrders() {

    return openOrders;
  }

  public void addOpenOrders(List<AbucoinsOrder> openOrders) {

    this.openOrders.addAll(openOrders);
  }

  public void addOpenOrder(AbucoinsOrder openOrder) {

    this.openOrders.add(openOrder);
  }

  @Override
  public String toString() {

    return "AbucoinsOpenOrders [openOrders=" + openOrders + "]";
  }

  static class AbucoinsOpenOrdersDeserializer extends JsonDeserializer<AbucoinsOpenOrders> {

    @Override
    public AbucoinsOpenOrders deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode openOrdersNode = oc.readTree(jp);

      final JsonNode errorNode = openOrdersNode.path("error");
      if (!errorNode.isMissingNode()) {
        final String errorText = errorNode.asText();
        if (errorText.equals("Invalid symbols pair")) {
          return new AbucoinsOpenOrders();
        } else {
          throw new ExchangeException("Unable to retrieve open orders because " + errorText);
        }
      }

      final List<AbucoinsOrder> openOrders = new ArrayList<>();
      if (openOrdersNode.isArray()) {
        for (JsonNode openOrderNode : openOrdersNode) {
          final long id = openOrderNode.path("id").asLong();
          final long time = openOrderNode.path("time").asLong();
          final Type type = Type.valueOf(openOrderNode.path("type").asText());
          final BigDecimal price = new BigDecimal(openOrderNode.path("price").asText());
          final BigDecimal amount = new BigDecimal(openOrderNode.path("amount").asText());
          final BigDecimal pending = new BigDecimal(openOrderNode.path("pending").asText());

          openOrders.add(new AbucoinsOrder(id, time, type, price, amount, pending, null));
        }
      }
      return new AbucoinsOpenOrders(openOrders);
    }
  }
}
