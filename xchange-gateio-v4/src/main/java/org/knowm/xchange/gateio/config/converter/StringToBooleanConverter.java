package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Converts string value "1" to {@code true}, rest to {@code false}
 */
public class StringToBooleanConverter extends StdConverter<String, Boolean> {

  @Override
  public Boolean convert(final String value) {
    return "1".equals(value);
  }
}
