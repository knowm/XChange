package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/** Describe a call rate limit as a number of calls per some time span. */
public class RateLimit implements Serializable {

  @JsonProperty("calls")
  public int calls = 1;

  @JsonProperty("time_span")
  public int timeSpan = 1;

  @JsonProperty("time_unit")
  @JsonDeserialize(using = TimeUnitDeserializer.class)
  public TimeUnit timeUnit = TimeUnit.SECONDS;

  /** Constructor */
  public RateLimit() {}

  /**
   * Constructor
   *
   * @param calls
   * @param timeSpan
   * @param timeUnit
   */
  public RateLimit(
      @JsonProperty("calls") int calls,
      @JsonProperty("time_span") int timeSpan,
      @JsonProperty("time_unit") @JsonDeserialize(using = TimeUnitDeserializer.class)
          TimeUnit timeUnit) {

    this.calls = calls;
    this.timeUnit = timeUnit;
    this.timeSpan = timeSpan;
  }

  /**
   * @return this rate limit as a number of milliseconds required between any two remote calls,
   *     assuming the client makes consecutive calls without any bursts or breaks for an infinite
   *     period of time.
   */
  @JsonIgnore
  public long getPollDelayMillis() {
    return timeUnit.toMillis(timeSpan) / calls;
  }

  @Override
  public String toString() {
    return "RateLimit [calls=" + calls + ", timeSpan=" + timeSpan + ", timeUnit=" + timeUnit + "]";
  }

  public static class TimeUnitDeserializer extends JsonDeserializer<TimeUnit> {
    @Override
    public TimeUnit deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      return TimeUnit.valueOf(jp.getValueAsString().toUpperCase());
    }
  }
}
