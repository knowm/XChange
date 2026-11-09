package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.Instant;

/** Converts timestamp as double in milliseconds to {@code Instant} */
public class LongToInstantConverter extends StdConverter<Long, Instant> {

  @Override
  public Instant convert(final Long value) {
    return Instant.ofEpochMilli(value);
  }
}
