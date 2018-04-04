package org.knowm.xchange.coinbase.dto.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/** @author jamespedwards42 */
public class EnumLowercaseJsonSerializer extends JsonSerializer<Enum<?>> {

  @Override
  public void serialize(Enum<?> value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {

    jgen.writeString(value.toString().toLowerCase());
  }
}
