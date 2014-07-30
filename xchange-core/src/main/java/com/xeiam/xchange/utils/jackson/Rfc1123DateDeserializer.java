package com.xeiam.xchange.utils.jackson;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Deserializes an rfc1123 formatted Date String to a Java Date
 * rfc1123 format: 'EEE, dd MMM yyyy HH:mm:ss zzz'
 * 
 * @author jamespedwards42
 */
public class Rfc1123DateDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

    return DateUtils.fromRfc1123DateString(jp.getValueAsString(), Locale.US);
  }

}
