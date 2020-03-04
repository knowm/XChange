package org.knowm.xchange.stream.bitfinex.dto.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.knowm.xchange.stream.bitfinex.dto.BitfinexWebSocketUpdateOrderbook;
import java.io.IOException;

public class BitfinexWebSocketUpdateOrderbookDeserializer
    extends StdDeserializer<BitfinexWebSocketUpdateOrderbook> {

  private static final long serialVersionUID = -5932404683677998182L;

  protected BitfinexWebSocketUpdateOrderbookDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public BitfinexWebSocketUpdateOrderbook deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    return null;
  }
}
