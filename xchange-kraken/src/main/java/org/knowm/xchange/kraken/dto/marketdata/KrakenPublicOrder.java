package org.knowm.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder.KrakenOrderDeserializer;

@JsonDeserialize(using = KrakenOrderDeserializer.class)
public class KrakenPublicOrder {

  private final BigDecimal price;
  private final BigDecimal volume;
  private final long timestamp;

  public KrakenPublicOrder(BigDecimal price, BigDecimal volume, long timestamp) {

    this.price = price;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "KrakenOrder [price=" + price + ", volume=" + volume + ", timestamp=" + timestamp + "]";
  }

  static class KrakenOrderDeserializer extends JsonDeserializer<KrakenPublicOrder> {

    @Override
    public KrakenPublicOrder deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isArray()) {
        BigDecimal price = new BigDecimal(node.path(0).asText());
        BigDecimal volume = new BigDecimal(node.path(1).asText());
        long timestamp = node.path(2).asLong();

        return new KrakenPublicOrder(price, volume, timestamp);
      }

      return null;
    }
  }
}
