package org.knowm.xchange.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** @author Matija Mazi */
public class SqlUtcTimeDeserializer extends JsonDeserializer<Date> {

  private SimpleDateFormat dateFormat;

  public SqlUtcTimeDeserializer() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

    String str = jp.getValueAsString();
    try {
      return dateFormat.parse(str);
    } catch (ParseException e) {
      throw new InvalidFormatException("Error parsing as date", str, Date.class);
    }
  }
}
