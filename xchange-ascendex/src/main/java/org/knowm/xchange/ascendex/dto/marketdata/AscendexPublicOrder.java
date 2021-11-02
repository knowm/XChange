package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;

@JsonDeserialize(using = AscendexPublicOrder.AscendexOrderDeserializer.class)
public class AscendexPublicOrder {

  private final BigDecimal price;
  private final BigDecimal volume;

  public AscendexPublicOrder(BigDecimal price, BigDecimal volume) {

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
    return "AscendexPublicOrder{" + "price=" + price + ", volume=" + volume + '}';
  }

  static class AscendexOrderDeserializer extends JsonDeserializer<AscendexPublicOrder> {

    @Override
    public AscendexPublicOrder deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isArray()) {
        BigDecimal price = new BigDecimal(node.path(0).asText());
        BigDecimal volume = new BigDecimal(node.path(1).asText());

        return new AscendexPublicOrder(price, volume);
      }

      return null;
    }
  }
}
