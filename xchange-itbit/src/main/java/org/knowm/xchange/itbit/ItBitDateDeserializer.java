package org.knowm.xchange.itbit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ItBitDateDeserializer extends JsonDeserializer<Date> {

  private SimpleDateFormat simpleDateFormat;

  {
    // For some reason, "yyyy-MM-dd'T'HH:mm:ss.SSS'0000'X" doesn't work, and neither does it without
    // the quotes.
    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Override
  public Date deserialize(JsonParser jp, final DeserializationContext ctxt) throws IOException {

    String value = jp.getValueAsString().replaceFirst("0000Z$", "");
    try {
      return simpleDateFormat.parse(value);
    } catch (ParseException e) {
      throw new InvalidFormatException(
          jp, "Can't parse date at offset " + e.getErrorOffset(), value, Date.class);
      //      throw new RuntimeException("Can't parse date at offset " + e.getErrorOffset(), e);
    }
  }
}
