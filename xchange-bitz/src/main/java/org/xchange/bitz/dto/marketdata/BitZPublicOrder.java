package org.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import org.xchange.bitz.dto.marketdata.BitZPublicOrder.BitZOrderDeserializer;

@JsonDeserialize(using = BitZOrderDeserializer.class)
public class BitZPublicOrder {

  private final BigDecimal price;
  private final BigDecimal volume;

  public BitZPublicOrder(BigDecimal price, BigDecimal volume) {
    this.price = price;
    this.volume = volume;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  static class BitZOrderDeserializer extends JsonDeserializer<BitZPublicOrder> {

    @Override
    public BitZPublicOrder deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      ObjectCodec oc = p.getCodec();
      JsonNode node = oc.readTree(p);

      if (node.isArray()) {
        BigDecimal price = new BigDecimal(node.path(0).asText());
        BigDecimal volume = new BigDecimal(node.path(1).asText());

        return new BitZPublicOrder(price, volume);
      }

      return null;
    }
  }
}
