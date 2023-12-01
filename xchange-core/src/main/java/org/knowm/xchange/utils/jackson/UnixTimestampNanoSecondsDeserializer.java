package org.knowm.xchange.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.utils.DateUtils;

/**
 * Converts double that represents unit milliseconds timestamp to Date. e.g. "1657444151.611" is
 * converted to "Sun Jul 10 11:09:11 CEST 2022"
 */
public class UnixTimestampNanoSecondsDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    String value = jp.getValueAsString();
    long valueWithMilliseconds =
        new BigDecimal(value).multiply(BigDecimal.valueOf(1000)).longValue();
    return DateUtils.fromUnixTimeWithMilliseconds(valueWithMilliseconds);
  }
}
