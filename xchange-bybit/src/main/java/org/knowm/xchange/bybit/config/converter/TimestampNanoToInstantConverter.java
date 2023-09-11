package org.knowm.xchange.bybit.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.Instant;

/**
 * Converts timestamp in seconds to {@code Instant}
 */
public class TimestampNanoToInstantConverter extends StdConverter<Long, Instant> {

  @Override
  public Instant convert(final Long value) {
    return Instant.ofEpochSecond(0L, value);
  }
}
