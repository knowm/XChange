package org.knowm.xchange.bitstamp.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Date;
import org.knowm.xchange.bitstamp.BitstampUtils;

/** Converts string value to {@code Date} using BitstampUtils.parseDate */
public class StringToDateConverter extends StdConverter<String, Date> {

  @Override
  public Date convert(String value) {
    return BitstampUtils.parseDate(value);
  }
}
