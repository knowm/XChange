package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@JsonDeserialize(using = CryptowatchPublicOrder.CryptowatchPublicOrderDeserializer.class)
@Getter
@AllArgsConstructor
@ToString
public class CryptowatchPublicOrder {

  private final BigDecimal price;
  private final BigDecimal volume;

  static class CryptowatchPublicOrderDeserializer extends JsonDeserializer<CryptowatchPublicOrder> {

    @Override
    public CryptowatchPublicOrder deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      if (node.isArray()) {
        BigDecimal price = new BigDecimal(node.path(0).asText());
        BigDecimal volume = new BigDecimal(node.path(1).asText());
        return new CryptowatchPublicOrder(price, volume);
      }

      return null;
    }
  }
}
