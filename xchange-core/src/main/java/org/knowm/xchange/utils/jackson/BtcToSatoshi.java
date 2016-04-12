package org.knowm.xchange.utils.jackson;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author timmolter
 */
public class BtcToSatoshi extends JsonSerializer<BigDecimal> {

  @Override
  public void serialize(BigDecimal valueBtc, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeNumber(valueBtc.movePointRight(8));
  }
}
