package org.knowm.xchange.coinbene;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.math.BigDecimal;

public class CoinbeneBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

  @Override
  public BigDecimal deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {

    String value = jsonParser.getValueAsString();

    if ("--".equals(value)) {
      return BigDecimal.ZERO;
    } else {
      return new BigDecimal(value);
    }
  }
}
