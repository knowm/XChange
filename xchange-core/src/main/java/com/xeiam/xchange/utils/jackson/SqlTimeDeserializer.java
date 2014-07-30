package com.xeiam.xchange.utils.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * @author Matija Mazi
 */
public class SqlTimeDeserializer extends JsonDeserializer<Date> {

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    String str = jp.getValueAsString();
    try {
      return dateFormat.parse(str);
    } catch (ParseException e) {
      throw new InvalidFormatException("Error parsing as date", str, Date.class);
    }
  }
}
