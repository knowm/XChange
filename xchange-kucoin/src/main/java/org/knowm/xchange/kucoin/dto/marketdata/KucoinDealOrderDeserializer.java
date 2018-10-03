package org.knowm.xchange.kucoin.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.kucoin.dto.KucoinOrderType;

public class KucoinDealOrderDeserializer extends JsonDeserializer<KucoinDealOrder> {

  @Override
  public KucoinDealOrder deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode root = p.readValueAsTree();
    if (root.isArray()) {
      Long timestamp = root.get(0).asLong();
      KucoinOrderType orderType = KucoinOrderType.valueOf(root.get(1).asText());
      BigDecimal price = root.get(2).decimalValue();
      BigDecimal amount = root.get(3).decimalValue();
      BigDecimal volume = root.get(4).decimalValue();
      return new KucoinDealOrder(timestamp, orderType, price, amount, volume);
    } else {
      throw new RuntimeException("KucoinDealOrder should have an array as root node!");
    }
  }
}
