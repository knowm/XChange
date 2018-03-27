package org.knowm.xchange.utils.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Date;

/** @author timmolter */
public class UnixTimestampSerializer extends JsonSerializer<Date> {

  @Override
  public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeNumber(value.getTime() / 1000);
  }
}
