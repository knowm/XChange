package org.knowm.xchange.bitget.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.knowm.xchange.bitget.dto.trade.BitgetOrderInfoDto.FeeDetail;

public class FeeDetailDeserializer extends JsonDeserializer<FeeDetail> {

  @Override
  public FeeDetail deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String text = p.getText();
    ObjectMapper objectMapper = (ObjectMapper) p.getCodec();

    return objectMapper.readValue(text, FeeDetail.class);
  }
}
