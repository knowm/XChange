package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

/**
 * Converts timestamp in seconds to {@code Instant}
 */
public class DoubleMillisecondsToInstantConverter extends StdConverter<Double, Instant> {

  @Override
  public Instant convert(final Double value) {
    return Instant.ofEpochMilli(Double.valueOf(value * 1000).longValue());
  }
}
