package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.gateio.dto.marketdata.GateioPublicOrder.GateioPublicOrderDeserializer;

@JsonDeserialize(using = GateioPublicOrderDeserializer.class)
public class GateioPublicOrder {

  private final BigDecimal price;
  private final BigDecimal amount;

  private GateioPublicOrder(BigDecimal price, final BigDecimal amount) {

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

    return "GateioPublicOrder [price=" + price + ", amount=" + amount + "]";
  }

  static class GateioPublicOrderDeserializer extends JsonDeserializer<GateioPublicOrder> {

    @Override
    public GateioPublicOrder deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode tickerNode = oc.readTree(jp);

      final BigDecimal price = new BigDecimal(tickerNode.path(0).asText());
      final BigDecimal amount = new BigDecimal(tickerNode.path(1).asText());

      return new GateioPublicOrder(price, amount);
    }
  }
}
