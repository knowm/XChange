package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.Instant;

/**
 * Converts timestamp as double in milliseconds to {@code Instant}
 */
public class DoubleToInstantConverter extends StdConverter<Double, Instant> {

  @Override
  public Instant convert(final Double value) {
    return Instant.ofEpochMilli(value.longValue());
  }
}
