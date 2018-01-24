package org.knowm.xchange.kucoin.dto.trading;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

import org.knowm.xchange.kucoin.dto.KucoinOrderType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class KucoinActiveOrderDeserializer extends JsonDeserializer<KucoinActiveOrder> {

  @Override
  public KucoinActiveOrder deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode root = p.readValueAsTree();
    if (root.isArray()) {
      Date timestamp = new Date(root.get(0).asLong());
      KucoinOrderType orderType = KucoinOrderType.valueOf(root.get(1).asText());
      BigDecimal price = root.get(2).decimalValue();
      BigDecimal amount = root.get(3).decimalValue();
      BigDecimal dealAmount = root.get(4).decimalValue(); // amount already filled
      String orderOid = root.get(5).textValue();
      return new KucoinActiveOrder(timestamp, orderType, price, amount, dealAmount, orderOid);
    } else {
      throw new RuntimeException("KucoinDealOrder should have an array as root node!");
    }
  }

}
