package org.knowm.xchange.bter.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.bter.dto.marketdata.BTERPublicOrder.BTERPublicOrderDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BTERPublicOrderDeserializer.class)
public class BTERPublicOrder {

  private final BigDecimal price;
  private final BigDecimal amount;

  private BTERPublicOrder(BigDecimal price, final BigDecimal amount) {

    this.price = price;
    this.amount = amount;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "BTERPublicOrder [price=" + price + ", amount=" + amount + "]";
  }

  static class BTERPublicOrderDeserializer extends JsonDeserializer<BTERPublicOrder> {

    @Override
    public BTERPublicOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode tickerNode = oc.readTree(jp);

      final BigDecimal price = new BigDecimal(tickerNode.path(0).asText());
      final BigDecimal amount = new BigDecimal(tickerNode.path(1).asText());

      return new BTERPublicOrder(price, amount);
    }

  }
}
