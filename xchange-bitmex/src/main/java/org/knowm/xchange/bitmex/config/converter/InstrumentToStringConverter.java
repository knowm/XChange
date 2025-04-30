package org.knowm.xchange.bitmex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.instrument.Instrument;

/** Converts {@code Instrument} to String */
public class InstrumentToStringConverter extends StdConverter<Instrument, String> {

  @Override
  public String convert(Instrument value) {
    return BitmexAdapters.toString(value);
  }
}
