package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

/**
 * Converts timestamp in milliseconds to {@code Instant}
 */
public class StringMillisecondsToInstantConverter extends StdConverter<String, Instant> {

  @Override
  public Instant convert(final String value) {
    return Instant.ofEpochMilli((long) (Double.parseDouble(value)));
  }
}

