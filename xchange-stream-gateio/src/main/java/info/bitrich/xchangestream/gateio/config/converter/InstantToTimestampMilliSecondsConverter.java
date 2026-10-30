package info.bitrich.xchangestream.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

/**
 * Converts {@code Instant} to timestamp in MilliSeconds
 */
public class InstantToTimestampMilliSecondsConverter extends StdConverter<Instant, Long> {

  @Override
  public Long convert(Instant value) {
    return value.toEpochMilli();
  }

}
