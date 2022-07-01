package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonDeserialize(using = GlobitexOrder.GlobitexOrderDeserializer.class)
public class GlobitexOrder implements Serializable {

  private final BigDecimal price;

  private final BigDecimal volume;

  public GlobitexOrder(BigDecimal price, BigDecimal volume) {
    this.price = price;
    this.volume = volume;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "GlobitexOrder{" + "price=" + price + ", volume=" + volume + '}';
  }

  static class GlobitexOrderDeserializer extends JsonDeserializer<GlobitexOrder> {

    @Override
    public GlobitexOrder deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isArray()) {
        BigDecimal price = new BigDecimal(node.path(0).asText());
        BigDecimal volume = new BigDecimal(node.path(1).asText());

        return new GlobitexOrder(price, volume);
      }

      return null;
    }
  }
}
