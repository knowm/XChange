package info.bitrich.xchangestream.bitget.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.Instant;

/** Converts {@code Instant} to timestamp in seconds */
public class InstantToTimestampSecondsConverter extends StdConverter<Instant, Long> {

  @Override
  public Long convert(Instant value) {
    return value.getEpochSecond();
  }
}
