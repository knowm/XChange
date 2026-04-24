package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.instrument.Instrument;

public class InstrumentToStringConverter extends StdConverter<Instrument, String> {
  @Override
  public String convert(Instrument value) {
    return value.getBase() + "_" + value.getCounter();
  }
}
