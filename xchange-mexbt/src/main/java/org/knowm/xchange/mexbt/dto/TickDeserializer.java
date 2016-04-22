package org.knowm.xchange.mexbt.dto;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * A single tick represents one hundred nanoseconds or one ten-millionth of a second. There are 10,000 ticks in a millisecond, or 10 million ticks in
 * a second. A tick represents the number of 100-nanosecond intervals that have elapsed since 12:00:00 midnight, January 1, 0001 (0:00:00 UTC on
 * January 1, 0001, in the Gregorian calendar). It does not include the number of ticks that are attributable to leap seconds.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/system.datetime.ticks(v=vs.110).aspx">DateTime.Ticks Property</a>
 */
public class TickDeserializer extends JsonDeserializer<Date> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    long tick = jp.getLongValue();
    return new Date((tick - 621355968000000000L) / 10000);
  }

}
