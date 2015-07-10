package com.xeiam.xchange.dto.meta;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Rafał Krupiński
 */
public class RateLimit {
  @JsonProperty
  public int calls = 1;

  @JsonDeserialize(using = TimeUnitDeserializer.class)
  public TimeUnit timeUnit = TimeUnit.SECONDS;

  @JsonProperty
  public int timeSpan = 1;

  public static class TimeUnitDeserializer extends JsonDeserializer<TimeUnit> {
    @Override
    public TimeUnit deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      return TimeUnit.valueOf(jp.getValueAsString().toUpperCase());
    }
  }

}
