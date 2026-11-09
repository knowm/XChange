package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;
/** Converts timestamp in seconds to {@code Instant} */
public class StringSecondsToInstantConverter extends StdConverter<String, Instant> {

  @Override
  public Instant convert(final String value) {
    return Instant.ofEpochSecond((long) (Double.parseDouble(value)));
  }
}
