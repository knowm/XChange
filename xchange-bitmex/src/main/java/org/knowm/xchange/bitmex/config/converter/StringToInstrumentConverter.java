package org.knowm.xchange.bitmex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.instrument.Instrument;

/** Converts string to {@code Instrument} */
public class StringToInstrumentConverter extends StdConverter<String, Instrument> {

  @Override
  public Instrument convert(String value) {
    return BitmexAdapters.toInstrument(value);
  }
}
