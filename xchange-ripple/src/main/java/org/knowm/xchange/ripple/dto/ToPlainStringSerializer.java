package org.knowm.xchange.ripple.dto;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ToPlainStringSerializer extends JsonSerializer<BigDecimal> {
  @Override
  public void serialize(final BigDecimal value, final JsonGenerator jgen,
      final SerializerProvider provider) throws IOException, JsonProcessingException {
    jgen.writeString(value.stripTrailingZeros().toPlainString());
  }
}
