package org.knowm.xchange.bitmex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.instrument.Instrument;

/**
 * Converts {@code Instrument} to String
 */
public class InstrumentListToStringConverter extends StdConverter<List<Instrument>, List<String>> {

  @Override
  public List<String> convert(List<Instrument> value) {
    if (value == null) {
      return null;
    }
    return value.stream()
        .map(BitmexAdapters::toString)
        .collect(Collectors.toList());
  }
}
