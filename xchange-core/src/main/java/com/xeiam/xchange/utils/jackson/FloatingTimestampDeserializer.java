package com.xeiam.xchange.utils.jackson;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author Matija Mazi
 */
public class FloatingTimestampDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    return new Date(Math.round(jp.getValueAsDouble() * 1000));
  }
}
