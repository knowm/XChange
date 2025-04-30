package org.knowm.xchange.bitget.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.apache.commons.lang3.BooleanUtils;

/** Converts string value to {@code Boolean} */
public class StringToBooleanConverter extends StdConverter<String, Boolean> {

  @Override
  public Boolean convert(final String value) {
    return BooleanUtils.toBoolean(value);
  }
}
