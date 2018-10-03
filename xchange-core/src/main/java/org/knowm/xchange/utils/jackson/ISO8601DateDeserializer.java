package org.knowm.xchange.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.utils.DateUtils;

/**
 * Deserializes an ISO 8601 formatted Date String to a Java Date ISO 8601 format:
 * yyyy-MM-dd'T'HH:mm:ssX
 *
 * @author jamespedwards42
 */
public class ISO8601DateDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jp, final DeserializationContext ctxt) throws IOException {

    return DateUtils.fromISO8601DateString(jp.getValueAsString());
  }
}
