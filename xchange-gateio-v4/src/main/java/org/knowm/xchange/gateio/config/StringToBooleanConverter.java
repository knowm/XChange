package org.knowm.xchange.gateio.config;

import com.fasterxml.jackson.databind.util.StdConverter;

public class StringToBooleanConverter extends StdConverter<String, Boolean> {

  @Override
  public Boolean convert(final String value) {
    return "1".equals(value);
  }
}
